package application;


import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		Department obj = new Department(1, "Books");
		
		//Seller seller = new Seller(21, "Marcela", "marcela@gmail.com", new Date(), 3000.0, obj);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("====TEST 1: seller findById ====");
			
		Seller seller = sellerDao.findById(3); //buscando no banco de dados o vendedor de id 3
		
		if(seller == null) {
			System.out.println("Vendedor nao cadastrado! faça se o favor de prestar mais atençao seu fi de deus...humoooor vamos rii");
		}
		//System.out.println(obj);
		System.out.println(seller);
		
		
		System.out.println("\n====TEST 2: seller findByDepartment ====");
		Department department = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		for(Seller obj2 : list) {
			System.out.println(obj2);
		}
		
		System.out.println("\n====TEST 3: seller findAll ====");
		 list = sellerDao.findAll();
		for(Seller obj2 : list) {
			System.out.println(obj2);		
		}
		
		System.out.println("\n====TEST 4: seller insert ====");
		 Seller newSeler = new Seller(null, "Greg", "greg@antigo.com", new Date(), 4000.0, department);		
		 
		 sellerDao.insert(newSeler);//mandando inserir no banco
		 
		 System.out.println("Inserted ! New id = " + newSeler.getId());
		 
		
		
	
		System.out.println("\n====TEST 5: seller upadate ====");
		
		 seller = sellerDao.findById(1);
		seller.setName("Martha Waine");//setando um novo nome
		
		sellerDao.update(seller);//atualizando os dados passando este objeto seller como argumento
		 
		 System.out.println("Update completed!! " );
		 
		 System.out.println("\n====TEST 6: seller delete ====");
		 
		 System.out.println("enter id for delete test:");
		 Integer id = sc.nextInt();
		 sellerDao.deleteById(id);
		 System.out.println("Delete completed!!");
		 
		 sc.close();
	}
	
		
		
	}


