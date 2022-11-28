package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		/*desta forma minha classe ela vai expor um metodo q retorna o tipo da interface  mas internamente ela vai instanciar uma implementaçao
		é um macete para nao precisar expor a implementaçao e deixa somente a interface*/
		
		//DB hf = new DB();
		
		return new SellerDaoJDBC(DB.getConnection());//é obrigado passar uma conecçao como argumento, a classe responsavel por isso aqui no projeto é a DB
	}
}
