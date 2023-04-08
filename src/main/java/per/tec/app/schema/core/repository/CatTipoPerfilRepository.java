package per.tec.app.schema.core.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import per.tec.app.schema.core.entities.CatTipoPerfil;

public interface CatTipoPerfilRepository extends PagingAndSortingRepository<CatTipoPerfil, Serializable> {

	public abstract List<CatTipoPerfil> findAll();

	public abstract List<CatTipoPerfil> findByIdTipoPerfil(Integer idTipoPerfil);

	public abstract boolean existsByIdTipoPerfil(Integer idTipoPerfil);

	public abstract List<CatTipoPerfil> findByDescripcion(String descripcion);

	public abstract boolean existsByDescripcion(String descripcion);

}
