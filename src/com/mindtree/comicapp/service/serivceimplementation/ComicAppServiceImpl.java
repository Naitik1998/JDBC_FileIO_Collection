package com.mindtree.comicapp.service.serivceimplementation;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mindtree.comicapp.dao.ComicAppDaoService;
import com.mindtree.comicapp.dao.daoimplementation.ComicAppDaoServiceImplemantation;
import com.mindtree.comicapp.entity.SuperHero;
import com.mindtree.comicapp.entity.Univers;
import com.mindtree.comicapp.exception.dao.ComicAppDaoException;
import com.mindtree.comicapp.exception.service.ComicAppServiceException;
import com.mindtree.comicapp.exception.service.custom.ErrorInDeSerializationDataException;
import com.mindtree.comicapp.exception.service.custom.ErrorInSerializationDataException;
import com.mindtree.comicapp.exception.service.custom.ErrorInWritingFileException;
import com.mindtree.comicapp.exception.service.custom.ErrorWhileReadingExcelFile;
import com.mindtree.comicapp.exception.service.custom.FileNotExistsException;
import com.mindtree.comicapp.exception.service.custom.SuperHeroAlreadyExistsException;
import com.mindtree.comicapp.exception.service.custom.SuperHeroNotFoundException;
import com.mindtree.comicapp.exception.service.custom.UniverAlreadyExistsException;
import com.mindtree.comicapp.exception.service.custom.UniversNotFoundException;
import com.mindtree.comicapp.service.ComicAppService;

public class ComicAppServiceImpl implements ComicAppService {

	ComicAppDaoService comicAppDaoService = new ComicAppDaoServiceImplemantation();

	/* Adding Universe */
	@Override
	public String addUnivers(Univers univers) throws ComicAppServiceException {
		String mString = null;

		try {
			if (comicAppDaoService.validateUnivers(univers)) {
				try {
					mString = comicAppDaoService.addUniversToDB(univers);
				} catch (ComicAppDaoException e) {
					throw new ComicAppServiceException(e.getMessage(), e);
				}

				return mString;
			} else {
				throw new UniverAlreadyExistsException("Univers Already Exists !!!");
			}
		} catch (ComicAppDaoException e) {
			throw new ComicAppServiceException(e.getMessage(), e);
		}
	}

	/* Adding SuperHero */
	@Override
	public String addSuperHero(SuperHero superHero) throws ComicAppServiceException {

		String mString = null;

		try {
			if (comicAppDaoService.validateSuperHero(superHero.getSuperHeroName())) {

				try {
					mString = comicAppDaoService.addSuperHeroToDB(superHero);
				} catch (ComicAppDaoException e) {
					throw new ComicAppServiceException(e.getMessage(), e);
				}

				return mString;
			} else {
				throw new SuperHeroAlreadyExistsException("Super Hero Already Exists !!!");
			}
		} catch (ComicAppDaoException e) {
			throw new ComicAppServiceException(e.getMessage(), e);
		}
	}

	/* Assigning Super Hero To univers */
	@Override
	public String assignSuperHeroToUnivers(String spName, long universId) throws ComicAppServiceException {
		String mString = null;

		try {
			if (!comicAppDaoService.validateSuperHero(spName)) {
				if (comicAppDaoService.validateUnivers(universId)) {
					try {
						mString = comicAppDaoService.assignSuperHeroToUniversInDB(spName, universId);
					} catch (ComicAppDaoException e) {
						throw new ComicAppServiceException(e.getMessage(), e);
					}
					return mString;
				} else {
					throw new UniversNotFoundException("Univers Not Found !!!");
				}
			} else {
				throw new SuperHeroNotFoundException("Super Hero Not Found !!!");
			}
		} catch (ComicAppDaoException e) {
			throw new ComicAppServiceException(e.getMessage(), e);

		}

	}

	/* Getting Super Hero By Univers Name */
	@Override
	public Set<SuperHero> displaySuperHeroByUniversName(String universName1) throws ComicAppServiceException {

		long universId = 0;
		Set<SuperHero> superHerolist;
		try {
			universId = comicAppDaoService.getUniversId(universName1);

			if (universId == 0) {
				throw new UniversNotFoundException("Universe Not Found");
			} else {
				superHerolist = comicAppDaoService.getSuperHeroByUniversName(universId);
//				Univers univers = new Univers();
//				univers.setSuperHeroList(superHerolist);

				if (superHerolist == null)
					throw new SuperHeroNotFoundException("Super Hero not found in " + universName1 + " !!!");

				return superHerolist;
			}

		} catch (ComicAppDaoException e) {
			throw new ComicAppServiceException(e.getMessage(), e);

		}

	}

	/* Updatin super Hero HP */
	@Override
	public String updateSuperHeroHP(String sname, long hp1) throws ComicAppServiceException {

		try {
			if (!comicAppDaoService.validateSuperHero(sname)) {

				String mString = comicAppDaoService.updateSuperHeroHpInDB(sname, hp1);

				return mString;
			} else {
				throw new SuperHeroNotFoundException("Super Hero Not Found !!!");
			}
		} catch (ComicAppDaoException e) {
			throw new ComicAppServiceException(e.getMessage(), e);

		}

	}

	/* Getting Super Hero With Max HP By univers Name */
	@Override
	public SuperHero displaySuperHeroWithMaxHpByUniversName(String universName2) throws ComicAppServiceException {

		long universId = 0;
		SuperHero superHero;
		try {
			universId = comicAppDaoService.getUniversId(universName2);

			if (universId == 0) {
				throw new UniversNotFoundException("Universe Not Found");
			} else {
				superHero = comicAppDaoService.getSuperHeroWithMaxHpByUniversName(universId);

				if (superHero == null)
					throw new SuperHeroNotFoundException("Super Hero not found in " + universName2 + " !!!");

				return superHero;
			}

		} catch (ComicAppDaoException e) {
			throw new ComicAppServiceException(e.getMessage(), e);

		}

	}

	/* Buffer Writer and File Writer */
	@Override
	public String writingIntoFile(String universName1, Set<SuperHero> superHeros) throws ComicAppServiceException {
		// TODO Auto-generated method stub
		try {
			File file = new File("D:\\JDBCTWOEntityTest0703\\ComicApp\\superherodetails.txt");

			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write("Univers Name: " + universName1 + "\n");
			for (SuperHero sHero : superHeros) {

				bufferedWriter.write(sHero.toString() + "\n");

			}
			bufferedWriter.close();
		} catch (IOException e) {
			throw new ErrorInWritingFileException("Error in Writing File !!!", e);
		}

		return "File Writing Is Done !!!";

	}

	/* Buffer Reader and File Reader */
	public BufferedReader fileReading() throws ComicAppServiceException {

		File file = new File("D:\\JDBCTWOEntityTest0703\\ComicApp\\superherodetails.txt");

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			throw new FileNotExistsException("File not found !!!", e);
		}

		return br;

	}

	/* Retrieving SuperHero data with universe */
	public Map<SuperHero, Univers> getAllDataOFSuperheroWithUnivers() throws ComicAppServiceException {
		Map<SuperHero, Univers> hashMap = null;
		try {
			hashMap = comicAppDaoService.getUniversAndSuperheroFromDB();
		} catch (ComicAppDaoException e) {
			throw new ComicAppServiceException(e.getMessage(), e);
		}

		return hashMap;

	}

	/* Writing Into Excel File */
	public String writeIntoExcel() throws ComicAppServiceException {

		try {
			Map<SuperHero, Univers> map = comicAppDaoService.getUniversAndSuperheroFromDB();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Comic APP Details");

			Row row;
			row = sheet.createRow(0);
			Cell cell01 = row.createCell(0);
			cell01.setCellValue("SH_ID");
			Cell cell02 = row.createCell(1);
			cell02.setCellValue("SH_NAME");
			Cell cell03 = row.createCell(2);
			cell03.setCellValue("SH_HP");
			Cell cell04 = row.createCell(3);
			cell04.setCellValue("U_Id");
			Cell cell05 = row.createCell(4);
			cell05.setCellValue("U_NAME");

			int rownum = 1;

			for (Map.Entry<SuperHero, Univers> mapdata : map.entrySet()) {

				row = sheet.createRow(rownum++);
				Cell cell1 = row.createCell(0);
				cell1.setCellValue(mapdata.getKey().getSuperHeroId());
				Cell cell2 = row.createCell(1);
				cell2.setCellValue(mapdata.getKey().getSuperHeroName());
				Cell cell3 = row.createCell(2);
				cell3.setCellValue(mapdata.getKey().getHp());
				Cell cell4 = row.createCell(3);
				cell4.setCellValue(mapdata.getValue().getUniversId());
				Cell cell5 = row.createCell(4);
				cell5.setCellValue(mapdata.getValue().getUniversName());

			}

			FileOutputStream fileOutputStream = new FileOutputStream(
					new File("D:\\JDBCTWOEntityTest0703\\ComicApp\\SuperHeroWithUnivers.xlsx"));
			workbook.write(fileOutputStream);

			fileOutputStream.close();
			workbook.close();

		} catch (IOException | ComicAppDaoException e) {

			throw new ComicAppServiceException(e.getMessage(), e);

		}
		return "Excel File Created Sucessfully !!!";

	}

	/* Read From Excel File */

	@SuppressWarnings("deprecation")
	public void readFromExcel() throws ComicAppServiceException {

		try {
			FileInputStream fileInputStream = new FileInputStream(
					"D:\\JDBCTWOEntityTest0703\\ComicApp\\SuperHeroWithUnivers.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet sheet = workbook.getSheet("Comic APP Details");

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {

				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.iterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						System.out.print(cell.getNumericCellValue() + "\t\t\t");

						break;

					case Cell.CELL_TYPE_STRING:
						System.out.print(cell.getStringCellValue() + "\t\t\t");
						break;
					}

				}
				System.out.println();

			}

			workbook.close();
			fileInputStream.close();

		} catch (IOException e) {
			throw new ErrorWhileReadingExcelFile(e.getMessage());
		}
	}

	/* Serialization of Data */
	@Override
	public String serializationData(SuperHero superHeroData) throws ComicAppServiceException {

		FileOutputStream fileOutputStream;
		BufferedOutputStream bufferedOutputStream;
		ObjectOutputStream objectOutputStream;
		try {
			fileOutputStream = new FileOutputStream(
					new File("D:\\JDBCTWOEntityTest0703\\ComicApp\\SerializationData.txt"));

			bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

			objectOutputStream = new ObjectOutputStream(bufferedOutputStream);

			
			objectOutputStream.writeObject(superHeroData);
			System.out.println(objectOutputStream.toString());
			bufferedOutputStream.flush();
			objectOutputStream.close();
			fileOutputStream.close();
		} catch (IOException e) {
			throw new ErrorInSerializationDataException("Error In Serialization Data");
		}
		return "Serialization of Data is Done";
	}

	
	/* DeSerialization of Data */
	@Override
	public void deserializationData() throws ComicAppServiceException {

		FileInputStream fileInputStream;
		BufferedInputStream bufferedInputStream;
		ObjectInputStream objectInputStream;

		try {
			fileInputStream = new FileInputStream("D:\\JDBCTWOEntityTest0703\\ComicApp\\SerializationData.txt");

			bufferedInputStream = new BufferedInputStream(fileInputStream);

			objectInputStream = new ObjectInputStream(bufferedInputStream);

			System.out.println(objectInputStream.readObject().toString());
			objectInputStream.close();
			bufferedInputStream.close();
			fileInputStream.close();
		} catch (IOException | ClassNotFoundException e) {
			throw new ErrorInDeSerializationDataException("Error In DeSerialization Data");
		}

	}

	// 

}
