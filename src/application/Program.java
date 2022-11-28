package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		
		
		Department obj = new Department(1, "Books");
		
		//Seller seller = new Seller(21, "Marcela", "marcela@gmail.com", new Date(), 3000.0, obj);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
			
		Seller seller = sellerDao.findById(3); //buscando no banco de dados o vendedor de id 3
		
		if(seller == null) {
			System.out.println("Vendedor nao cadastrado! faça se o favor de prestar mais atençao seu fi de deus...");
		}
		
		//System.out.println(obj);
		System.out.println(seller);
	}

}
