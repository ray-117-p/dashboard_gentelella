package per.tec.app.schema.core.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import per.tec.app.schema.core.entities.CatSchema;
import per.tec.app.schema.core.entities.CatTipoPerfil;
import per.tec.app.schema.core.entities.Perfil;

public interface PerfilRepository extends PagingAndSortingRepository<Perfil, Serializable> {

	public abstract List<Perfil> findAll();

	@Query("SELECT p FROM Perfil p INNER JOIN p.catTipoPerfil tp ON p.catTipoPerfil.idTipoPerfil = tp.idTipoPerfil WHERE tp.idTipoPerfil NOT IN(1)")
	public abstract List<Perfil> findAllSuperUserLimit();

	@Query("SELECT p FROM Perfil p INNER JOIN p.catTipoPerfil tp ON p.catTipoPerfil.idTipoPerfil = tp.idTipoPerfil INNER JOIN p.catSchema cs ON cs.idSchema=p.catSchema.idSchema WHERE tp.idTipoPerfil NOT IN(1) AND cs.idSchema = ?1")
	public abstract List<Perfil> findByIdSchema(int idSchema);

	public abstract List<Perfil> findByIdPerfil(int idPerfil);

	public abstract boolean existsByIdPerfil(int idPerfil);

	public abstract List<Perfil> findByDescripcion(String descripcion);

	public abstract boolean existsByDescripcion(String descripcion);

	public abstract List<Perfil> findByEstatus(Boolean estatus);

	public abstract boolean existsByEstatus(Boolean estatus);

	public abstract List<Perfil> findByIndex(String index);

	public abstract boolean existsByIndex(String index);

	public abstract List<Perfil> findByCatSchema(CatSchema catSchema);

	public abstract boolean existsByCatSchema(CatSchema catSchema);

	public abstract List<Perfil> findByCatTipoPerfil(CatTipoPerfil catTipoPerfil);

	public abstract boolean existsByCatTipoPerfil(CatTipoPerfil catTipoPerfil);

}
