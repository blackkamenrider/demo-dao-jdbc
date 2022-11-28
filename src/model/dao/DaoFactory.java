package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		/*desta forma minha classe ela vai expor um metodo q retorna o tipo da interface  mas internamente ela vai instanciar uma implementaçao
		é um macete para nao precisar expor a implementaçao e deixa somente a interface*/
		
		return new SellerDaoJDBC();
	}
}
