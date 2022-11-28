package model.dao;

import java.util.List;

import model.entities.Seller;

public interface SellerDao {

	void insert(Seller obj);  // será responsavel por inserir no banco de dados o obj q estiver recebendo como parametro 
	void update(Seller obj); 
	void deleteById(Integer id);
	Seller findById(Integer id); //será responsavel por pesquisar no banco de dados um ojb com este id do parametro
	List<Seller> findAll(); // retorna todos os departamentos
}
