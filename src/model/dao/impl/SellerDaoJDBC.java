package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	//injeçao de dependencia
	private Connection conn;
	
	//este contrutor é para forçar a injeçao de dependencia
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	
	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		
		PreparedStatement st = null;
		ResultSet rs = null; // ele traz do banco de dados as informaçoes em formato de tabela igual esta no banco de dados mysql	 	
/*a conecçao com o banco de dados (com o metodo Connection) será feita pela injeçao de dependencia que fiz em cima com o metodo construtor*/
		try {
		//este com a baixo e do conection declarado em cima antes do construtor	
			st = conn.prepareStatement(" SELECT seller.*,department.Name as DepName  " /* estou buscando todo campo do vendedor com seller.* + o nome do departamento com department.name e este nome do departamento estou dando um apelido com o depname */
					+ "FROM seller INNER JOIN department  "/*aqui fazendo um join para buscar os dados das duas tabelas (esta linha e a linha de baixo)*/
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?"); /*uma restriçao : onde o o id do vendedor seja igual ao id que vai vir no argumento da funçao*/
			
			st.setInt(1, id); //aqui eu digo q no primeiro '?' coloco a variável id
			
			rs = st.executeQuery(); // executa os comandos sql e coloque o resultado no rs (q é uma variável do tipo resultSet)
/* a minha classe dao é responsavel por pegar os dados em formato de linha e coluna do db relacional e transformar em objetos associados 
  neste caso aqui do meu programa ele vai criar um objeto com os dados do alex do tipo Seller e associado a ele vai ter outro objeto do tipo department com os dados do departamento dele
  em orientaçao a objetos mesmo eu buscando em formato de tabela, na memoria do comptador eu vou querer os objeto associados instanciado em memoria.
  para fazer isso irei fazer o seguinte: rs é nulo entao ele nao aponta para posiçao 0, para objeto nenhum, para estar apontando para um obj ele precisa apontar para um ou seja nao nulo
   */	
			
			if(rs.next()) {// testando se ainda é nulo ou nao. se nao for nulo é porq veio algum resultado do banco de dados
				//se entrou é porq reornou um resultado do alex e eu preciso navegar entre essa informaçao e instanciar os objetos, o vendedor e o departamento pendurado nele
				
				Department dep = instanciateDepartment(rs);
				
			//instanciei um departamento e setei os valores dele.(coloquei este codigo dentro de uma funçao e chamei essa funçao na linha de cima) agora vou criar um objeto seller apontando para o departamento
				
				Seller obj = instantiateSeller(rs, dep); // aqui estava todo o codigo de instanciaçao porém, coloquei ele dentro de uma funçao 
				return obj; //retornando o objeto seller que criei a cima
			}
			
			return null; // se o if deu false entao o vendedor com este id nao existe. vendedor é nulo 
			
		} 
		catch (Exception e) {
			throw new DbException (e.getMessage());
			// TODO: handle exception
		}
		
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			//DB.closeConnection(); conecçao eu nao fecho aqui, deixo pra fechar no programa principa porq ainda posso precisar dele aberto
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		
		Seller obj = new Seller();
		
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);//neste caso nao é o id do departamento e sim uma associaçao de objetos. quero um obj montado e o dep é o obj montado
		
		return obj;
	}


	private Department instanciateDepartment(ResultSet rs) throws SQLException {
	//possivell q de uma escessao porém onde uso este metodo eu já tratei a possíveis excessoes entao, propaguei com trows sqlexception para que quem a chamar a trate	
		    Department dep = new Department();
			dep.setId(rs.getInt("DepartmentId"));//este department dentro parametro é a coluna que veio do banco 
			dep.setName(rs.getString("DepName"));//a coluna que veio do banco onde esta o nome
			
			return dep;
	}


	@Override
	public List<Seller> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null; // ele traz do banco de dados as informaçoes em formato de tabela igual esta no banco de dados mysql	 	
/*a conecçao com o banco de dados (com o metodo Connection) será feita pela injeçao de dependencia que fiz em cima com o metodo construtor*/
		try {
		//este com a baixo e do conection declarado em cima antes do construtor	
			st = conn.prepareStatement(" SELECT seller.*,department.Name as DepName   " /* estou buscando todo campo do vendedor com seller.* + o nome do departamento com department.name e este nome do departamento estou dando um apelido com o depname */
					+ "FROM seller INNER JOIN department  "/*aqui fazendo um join para buscar os dados das duas tabelas (esta linha e a linha de baixo)*/
					+ "ON seller.DepartmentId = department.Id  "
					+ "ORDER BY Name"); 
			
			rs = st.executeQuery(); // executa os comandos sql e coloque o resultado no rs (q é uma variável do tipo resultSet)

			/* a minha classe dao é responsavel por pegar os dados em formato de linha e coluna do db relacional e transformar em objetos associados 
  neste caso aqui do meu programa ele vai criar um objeto com os dados do alex do tipo Seller e associado a ele vai ter outro objeto do tipo department com os dados do departamento dele
  em orientaçao a objetos mesmo eu buscando em formato de tabela, na memoria do comptador eu vou querer os objeto associados instanciado em memoria.
  para fazer isso irei fazer o seguinte: rs é nulo entao ele nao aponta para posiçao 0, para objeto nenhum, para estar apontando para um obj ele precisa apontar para um ou seja nao nulo
   */	
			
			
/* o resultado por ser mais um porq um departamento pode ter varias pessoas cadastradas entao preciso usar um while para fazer a busca*/
			List<Seller> list = new ArrayList<>();
			
			Map<Integer, Department> map = new HashMap<>();//criei esta estrutura para usar ela na logica de poder nao instanaciar mais de uma vez o departamento
			
			while(rs.next()) {// testando se ainda é nulo ou nao. se nao for nulo é porq veio algum resultado do banco de dados
				//se entrou é porq reornou um resultado do alex e eu preciso navegar entre essa informaçao e instanciar os objetos, o vendedor e o departamento pendurado nele
				
	            //aqui irei testar se o department já foi criado
				
				Department dep = map.get(rs.getInt("DepartmentId"));//busquei pelo id do departamento que veio do banco. se dep estiver nullo é porq nao tem este departamento criado enta, crio no if este departamento
				
				if(dep == null) {
					 dep = instanciateDepartment(rs);//esta linha precisa ser instanciada so uma vez porq o departamento so é um os vendedores q sao muitos
					 
					 //agora vou guardar dentro do meu map para que na proxima vez q for testado ele seja encontrado e nao instancie novamente
					 map.put(rs.getInt("DepartmentId"), dep);
				}
				
				
				
			//linha a baixo instanciei um departamento e setei os valores dele.(coloquei este codigo dentro de uma funçao e chamei essa funçao na linha de cima) agora vou criar um objeto seller apontando para o departamento
				
				Seller obj = instantiateSeller(rs, dep); // aqui estava todo o codigo de instanciaçao porém, coloquei ele dentro de uma funçao 
				list.add(obj);
			}
			
			return list; // quando sair do while a lista estará preenchida entao, eu retorno ela.
			
		} 
		catch (Exception e) {
			throw new DbException (e.getMessage());
			// TODO: handle exception
		}
		
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			//DB.closeConnection(); conecçao eu nao fecho aqui, deixo pra fechar no programa principa porq ainda posso precisar dele aberto
		}
		
		
	}


	@Override
	public List<Seller> findByDepartment(Department department) {
			
			PreparedStatement st = null;
			ResultSet rs = null; // ele traz do banco de dados as informaçoes em formato de tabela igual esta no banco de dados mysql	 	
	/*a conecçao com o banco de dados (com o metodo Connection) será feita pela injeçao de dependencia que fiz em cima com o metodo construtor*/
			try {
			//este com a baixo e do conection declarado em cima antes do construtor	
				st = conn.prepareStatement(" SELECT seller.*,department.Name as DepName   " /* estou buscando todo campo do vendedor com seller.* + o nome do departamento com department.name e este nome do departamento estou dando um apelido com o depname */
						+ "FROM seller INNER JOIN department  "/*aqui fazendo um join para buscar os dados das duas tabelas (esta linha e a linha de baixo)*/
						+ "ON seller.DepartmentId = department.Id  "
						+ "WHERE DepartmentId = ? " /*uma restriçao : onde o o id do vendedor seja igual ao id que vai vir no argumento da funçao*/
						+ "ORDER BY Name"); 
				
				st.setInt(1, department.getId());  //aqui eu digo q no primeiro '?' coloco a variável id
				
				rs = st.executeQuery(); // executa os comandos sql e coloque o resultado no rs (q é uma variável do tipo resultSet)
	
				/* a minha classe dao é responsavel por pegar os dados em formato de linha e coluna do db relacional e transformar em objetos associados 
	  neste caso aqui do meu programa ele vai criar um objeto com os dados do alex do tipo Seller e associado a ele vai ter outro objeto do tipo department com os dados do departamento dele
	  em orientaçao a objetos mesmo eu buscando em formato de tabela, na memoria do comptador eu vou querer os objeto associados instanciado em memoria.
	  para fazer isso irei fazer o seguinte: rs é nulo entao ele nao aponta para posiçao 0, para objeto nenhum, para estar apontando para um obj ele precisa apontar para um ou seja nao nulo
	   */	
				
				
	/* o resultado por ser mais um porq um departamento pode ter varias pessoas cadastradas entao preciso usar um while para fazer a busca*/
				List<Seller> list = new ArrayList<>();
				
				Map<Integer, Department> map = new HashMap<>();//criei esta estrutura para usar ela na logica de poder nao instanaciar mais de uma vez o departamento
				
				while(rs.next()) {// testando se ainda é nulo ou nao. se nao for nulo é porq veio algum resultado do banco de dados
					//se entrou é porq reornou um resultado do alex e eu preciso navegar entre essa informaçao e instanciar os objetos, o vendedor e o departamento pendurado nele
					
		            //aqui irei testar se o department já foi criado
					
					Department dep = map.get(rs.getInt("DepartmentId"));//busquei pelo id do departamento que veio do banco. se dep estiver nullo é porq nao tem este departamento criado enta, crio no if este departamento
					
					if(dep == null) {
						 dep = instanciateDepartment(rs);//esta linha precisa ser instanciada so uma vez porq o departamento so é um os vendedores q sao muitos
						 
						 //agora vou guardar dentro do meu map para que na proxima vez q for testado ele seja encontrado e nao instancie novamente
						 map.put(rs.getInt("DepartmentId"), dep);
					}
					
					
					
				//linha a baixo instanciei um departamento e setei os valores dele.(coloquei este codigo dentro de uma funçao e chamei essa funçao na linha de cima) agora vou criar um objeto seller apontando para o departamento
					
					Seller obj = instantiateSeller(rs, dep); // aqui estava todo o codigo de instanciaçao porém, coloquei ele dentro de uma funçao 
					list.add(obj);
				}
				
				return list; // quando sair do while a lista estará preenchida entao, eu retorno ela.
				
			} 
			catch (Exception e) {
				throw new DbException (e.getMessage());
				// TODO: handle exception
			}
			
			finally {
				DB.closeStatement(st);
				DB.closeResultSet(rs);
				//DB.closeConnection(); conecçao eu nao fecho aqui, deixo pra fechar no programa principa porq ainda posso precisar dele aberto
			}
	}

}
