package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {

	void insert(Department obj);  // será responsavel por inserir no banco de dados o obj q estiver recebendo como parametro 
	void update(Department obj); 
	void deleteById(Integer id);
	Department findById(Integer id); //será responsavel por pesquisar no banco de dados um ojb com este id do parametro
	List<Department> findAll(); // retorna todos os departamentos
}
