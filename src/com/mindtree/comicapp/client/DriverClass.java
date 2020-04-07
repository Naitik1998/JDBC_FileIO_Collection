package com.mindtree.comicapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.mindtree.comicapp.entity.SuperHero;
import com.mindtree.comicapp.entity.Univers;
import com.mindtree.comicapp.exception.service.ComicAppServiceException;
import com.mindtree.comicapp.service.ComicAppService;
import com.mindtree.comicapp.service.serivceimplementation.ComicAppServiceImpl;

public class DriverClass {

	public static void main(String[] args) {
		ComicAppService comicAppService = new ComicAppServiceImpl();

		Scanner sc = new Scanner(System.in);
		boolean flag = true;

		do {
			System.out.println();
			System.out.println("******************|| COMIC APP ||********************");
			System.out.println("Enter 1.	Add Univers");
			System.out.println("Enter 2.	Add Super hero");
			System.out.println("Enter 3.	Assign Super hero to univers.");
			System.out.println("Enter 4.	Display Super hero of given Univers, And Read Write in file");
			System.out.println("Enter 5.	Update HP of Given Super Hero.");
			System.out.println("Enter 6.	Get Super hero with max HP given Univers name.");
			System.out.println("Enter 7.	Get All Data Of superhero With Univers.");
			System.out.println("Enter 8:	Write And Read All Data of Superhero with univers in Excel.");
			System.out.println("Enter 9.	Exit.");
			System.out.println("Enter the choice:");
			int choice = sc.nextInt();
			System.out.println("********************************************************");
			switch (choice) {
			case 1:
				sc.nextLine();
				System.out.println("Enter the Univers Name:");
				String universName = sc.nextLine();

				Univers univers = new Univers(0, universName);

				try {
					String mString = comicAppService.addUnivers(univers);
					System.out.println(mString);
				} catch (ComicAppServiceException e) {
					System.out.println(e.getMessage());
				}

				break;
			case 2:
				sc.nextLine();
				System.out.println("Enter the Super Hero Name:");
				String superHeroName = sc.nextLine();

				System.out.println("Enter the HP of Super Hero:");
				long hp = sc.nextLong();

				SuperHero superHero = new SuperHero(0, superHeroName, hp);

				try {
					String mString = comicAppService.addSuperHero(superHero);
					System.out.println(mString);
				} catch (ComicAppServiceException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				sc.nextLine();
				System.out.println("Enter the Super Hero Name:");
				String spName = sc.nextLine();
				System.out.println("Enter the Univers Id to assign the Super Hero:");
				long universId = sc.nextLong();

				try {
					String mString = comicAppService.assignSuperHeroToUnivers(spName, universId);
					System.out.println(mString);
				} catch (ComicAppServiceException e) {
					System.out.println(e.getMessage());
				}

				break;
			case 4:
				sc.nextLine();
				System.out.println("Enter the univers Name: ");
				String universName1 = sc.nextLine();
				try {
					Set<SuperHero> superHerosHashSet = comicAppService.displaySuperHeroByUniversName(universName1);

					System.out.println("Superhero Data In Univers is (HashSet):");
					System.out.println("Univers Name: " + universName1);
					superHerosHashSet.forEach(superHero2 -> System.out.println(superHero2));

					System.out.println();
					Set<SuperHero> superHerosTreeSet = new TreeSet<SuperHero>(superHerosHashSet);
					System.out.println("Superhero Data In Univers is (TreeSet):");
					System.out.println("Univers Name: " + universName1);
					superHerosTreeSet.forEach(superHero2 -> System.out.println(superHero2));

					System.out.println();
					String filemessage = comicAppService.writingIntoFile(universName1, superHerosTreeSet);
					System.out.println(filemessage + "\n");

					System.out.println("File Reading.....");
					BufferedReader bufferedReader = comicAppService.fileReading();
					String st;
					while ((st = bufferedReader.readLine()) != null) {
						System.out.println(st);
					}
				} catch (ComicAppServiceException | IOException e) {
					System.out.println(e.getMessage());
				}

				break;
			case 5:
				sc.nextLine();
				System.out.println("Enter the Super Hero Name:");
				String sname = sc.nextLine();

				System.out.println("Enter the new HP:");
				long hp1 = sc.nextLong();

				try {
					String mString = comicAppService.updateSuperHeroHP(sname, hp1);
					System.out.println(mString);
				} catch (ComicAppServiceException e) {
					System.out.println(e.getMessage());
				}

				break;
			case 6:
				sc.nextLine();
				System.out.println("Enter the univers Name: ");
				String universName2 = sc.nextLine();
				try {
					SuperHero superHeroData = comicAppService.displaySuperHeroWithMaxHpByUniversName(universName2);
					System.out.println();
					System.out.println("Univers Name: " + universName2);
					System.out.println("Super hero with max HP:" + superHeroData);

					System.out.println();
					String mString = comicAppService.serializationData(superHeroData);
					System.out.println(mString);
					
					System.out.println();
					System.out.println("DeSerialization Of data is:");
					comicAppService.deserializationData();
				} catch (ComicAppServiceException e) {
					System.out.println(e.getMessage());
				}

				break;

			case 7:
				try {
					Map<SuperHero, Univers> superHeroUniversHashMap = comicAppService
							.getAllDataOFSuperheroWithUnivers();

					System.out.println("Retrieving SuperHero data with universe (HashMap):");

					superHeroUniversHashMap.entrySet()
							.forEach(map -> System.out.println(map.getKey() + "\t" + map.getValue()));

					Map<SuperHero, Univers> superHeroUniversTreeMap = new TreeMap<SuperHero, Univers>(
							comicAppService.getAllDataOFSuperheroWithUnivers());

					System.out.println();
					System.out.println("Retrieving SuperHero data with universe (TreeMap):");

					Iterator<Entry<SuperHero, Univers>> mapIterator = superHeroUniversTreeMap.entrySet().iterator();
					while (mapIterator.hasNext()) {
						Map.Entry<SuperHero, Univers> mapdata = (Map.Entry<SuperHero, Univers>) mapIterator.next();
						System.out.println(mapdata.getKey() + "\t" + mapdata.getValue());

					}

				} catch (ComicAppServiceException e) {
					System.out.println(e.getMessage());
				}

				break;

			case 8:
				try {
					System.out.println(comicAppService.writeIntoExcel());
					System.out.println();
					System.out.println("Reading Excel File.....");
					comicAppService.readFromExcel();
				} catch (ComicAppServiceException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 9:
				System.out.println("Thank You !!!");
				flag = false;
				break;

			default:
				System.out.println("Invalid Choice !!!");
				break;
			}

		} while (flag);

		sc.close();
	}

}
