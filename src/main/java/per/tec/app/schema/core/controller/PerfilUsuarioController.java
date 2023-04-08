package per.tec.app.schema.core.controller;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import per.tec.app.schema.core.entities.CatPermiso;
import per.tec.app.schema.core.entities.CatSchema;
import per.tec.app.schema.core.entities.CatTipoPerfil;
import per.tec.app.schema.core.entities.Perfil;
import per.tec.app.schema.core.entities.PerfilPermiso;
import per.tec.app.schema.core.service.CatPermisoService;
import per.tec.app.schema.core.service.CatSchemaService;
import per.tec.app.schema.core.service.CatTipoPerfilService;
import per.tec.app.schema.core.service.PerfilPermisoService;
import per.tec.app.schema.core.service.PerfilService;
import per.tec.app.schema.core.view.View;

@Controller
public class PerfilUsuarioController {

	private static final Logger logger = Logger.getLogger(PerfilUsuarioController.class);

	@Autowired
	private PerfilService perfilService;

	@Autowired
	private PerfilPermisoService perfilPermisoService;

	@Autowired
	private CatPermisoService catPermisoService;

	@Autowired
	private CatSchemaService catSchemaService;

	@Autowired
	private CatTipoPerfilService catTipoPerfilService;

	@Secured("ROLE_CORE_ALL_PROFILES")
	@GetMapping("/profiles")
	public String profiles(Model model) {
		List<Perfil> lstPerfilUsuario = perfilService.findAllPerfil();

		model.addAttribute("lstPerfilUsuario", lstPerfilUsuario);
		model.addAttribute("fragment", "fragment/core/profile/profiles.html");
		return View.INDEX;
	}

	@Secured("ROLE_CORE_CREATE_PROFILE")
	@GetMapping("/createProfile")
	public String createProfile(Model model) {

		List<CatPermiso> lstCatPermiso = catPermisoService.findAllCatPermiso();
		List<CatTipoPerfil> lstCatTipoPerfil = catTipoPerfilService.findAllCatTipoPerfil();
		List<CatSchema> lstCatSchema = catSchemaService.findAllCatSchema();
		Perfil nuevoPerfil = new Perfil();
		model.addAttribute("status", 1);
		model.addAttribute("nuevoPerfil", nuevoPerfil);
		model.addAttribute("lstCatPermiso", lstCatPermiso);
		model.addAttribute("lstCatTipoPerfil", lstCatTipoPerfil);
		model.addAttribute("lstCatSchema", lstCatSchema);
		model.addAttribute("fragment",
				"fragment/core/profile/add_profile.html");
		return View.INDEX;
	}

	@Secured("ROLE_CORE_CREATE_PROFILE")
	@PostMapping("/saveNuevoPerfil")
	public String saveNuevoPerfil(Model model, @ModelAttribute Perfil nuevoPerfil,
			RedirectAttributes redirectAttributes) {

		try {
			logger.info("-->" + nuevoPerfil);
			nuevoPerfil.setEstatus(true);
			perfilService.savePerfil(nuevoPerfil);
			List<CatPermiso> lstCatPermiso = catPermisoService.findAllCatPermiso();
			for (CatPermiso catPermiso : lstCatPermiso) {
				PerfilPermiso permiso = new PerfilPermiso();
				permiso.setPerfil(nuevoPerfil);
				permiso.setCatPermiso(catPermiso);
				for (PerfilPermiso perfilPermiso : nuevoPerfil.getPerfilPermisos()) {
					if (perfilPermiso.getCatPermiso().getIdPermiso() != null) {
						if (perfilPermiso.getCatPermiso().getIdPermiso() == catPermiso.getIdPermiso()) {
							permiso.setActivo(true);
							break;
						} else {
							permiso.setActivo(false);
						}
					}else {
						permiso.setActivo(false);
					}
				} // end of for
				perfilPermisoService.savePerfilPermiso(permiso);
			} // end of for

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorToast", " No es posible crear el perfil.");
			return "redirect:/profiles";
		}
		redirectAttributes.addFlashAttribute("successToast", " Perfil creado.");
		return "redirect:/profiles";
	}

	@Secured("ROLE_CORE_VIEW_PROFILE")
	@GetMapping("/editProfile/{idPerfil}")
	public String editProfile(Model model,
			@PathVariable(name = "idPerfil", required = true) int idPerfil,
			RedirectAttributes redirectAttributes) {

		List<CatPermiso> lstCatPermiso = catPermisoService.findAllCatPermiso();
		List<CatTipoPerfil> lstCatTipoPerfil = catTipoPerfilService.findAllCatTipoPerfil();
		List<CatSchema> lstCatSchema = catSchemaService.findAllCatSchema();
		Perfil nuevoPerfil = perfilService.findByIdPerfil(idPerfil).get(0);
		try {
			for (CatPermiso catPermiso : lstCatPermiso) {
				int bandera = 0;
				for (PerfilPermiso perfilPermiso : nuevoPerfil.getPerfilPermisos()) {
					if (perfilPermiso.getCatPermiso().getIdPermiso() == catPermiso.getIdPermiso()) {
						bandera = 1;
						break;
					} else {
						bandera = 0;
					}
				}
				if (bandera == 0) {
					PerfilPermiso permiso = new PerfilPermiso();
					permiso.setPerfil(nuevoPerfil);
					permiso.setCatPermiso(catPermiso);
					permiso.setActivo(false);
					perfilPermisoService.savePerfilPermiso(permiso);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorToast", " No es posible ver el perfil.");
			return "redirect:/profiles";
		}
		List<PerfilPermiso> lstPermisos = perfilPermisoService.findAllXIdPerfil(nuevoPerfil.getIdPerfil());
		nuevoPerfil.setPerfilPermisos(lstPermisos);
		model.addAttribute("status", 0);
		model.addAttribute("nuevoPerfil", nuevoPerfil);
		model.addAttribute("lstCatPermiso", lstCatPermiso);
		model.addAttribute("lstCatTipoPerfil", lstCatTipoPerfil);
		model.addAttribute("lstCatSchema", lstCatSchema);
		model.addAttribute("fragment",
				"fragment/core/profile/add_profile.html");
		return View.INDEX;
	}

	@Secured("ROLE_CORE_EDIT_PROFILE")
	@PostMapping("/saveEditarPerfil")
	public String saveEditarPerfil(Model model, @ModelAttribute Perfil nuevoPerfil,
			RedirectAttributes redirectAttributes) {
		try {
			for (PerfilPermiso perfilPermiso : nuevoPerfil.getPerfilPermisos()) {
				perfilPermisoService.savePerfilPermiso(perfilPermiso);
			}
			perfilService.savePerfil(nuevoPerfil);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorToast", " No es posible actualizar el perfil.");
			return "redirect:/profiles";
		}
		redirectAttributes.addFlashAttribute("successToast", " Perfil Actualizado.");
		return "redirect:/profiles";
	}
}
