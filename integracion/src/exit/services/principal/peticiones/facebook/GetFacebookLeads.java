package exit.services.principal.peticiones.facebook;

import java.io.BufferedReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import exit.services.fileHandler.CSVHandler;
import exit.services.json.JSONHandler;
import exit.services.principal.peticiones.AbstractHTTP;
import exit.services.principal.peticiones.ConvertidorJson;
import exit.services.principal.peticiones.vtex.funciones.FuncionesVTEX;
import exit.services.singletons.RecuperadorMapeoCsv;
import exit.services.singletons.RecuperadorPropiedadedConfiguracionEntidad;

public class GetFacebookLeads extends AbstractHTTP{

	@Override
	protected Object procesarPeticionOK(BufferedReader in, String id, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, String id, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionOK(BufferedReader in, int responseCode) throws Exception {
		FuncionesVTEX fvtex= new FuncionesVTEX();
		JSONObject jsonObject = ConvertidorJson.convertir(in);
		String[] buscar=RecuperadorMapeoCsv.getInstancia().getCuerpo().split(RecuperadorPropiedadedConfiguracionEntidad.getInstance().getSeparadorCSVREGEX());
		JSONArray jsonArrayIterar=(JSONArray)jsonObject.get(RecuperadorPropiedadedConfiguracionEntidad.getInstance().getIterarSobre());
		for(int index=0;index<jsonArrayIterar.size();index++){
			JSONObject jsonARecorrer=(JSONObject)jsonArrayIterar.get(index);
			ArrayList<Object> listaObjetos=new ArrayList<Object>();
			for(int j=0;j<buscar.length;j++){
				String[] arrayTipos=fvtex.getArrayTipos(buscar[j]);
				listaObjetos.add(fvtex.getValue(buscar[j],arrayTipos,jsonARecorrer));
			}
			String[] cabeceras=RecuperadorMapeoCsv.getInstancia().getCabecera().split(RecuperadorPropiedadedConfiguracionEntidad.getInstance().getSeparadorCSVREGEX());		
			StringBuilder sb = new StringBuilder();
			int cont=0;
			for(Object o:listaObjetos){
				String value = "";
				if(o!=null){
					if(cabeceras.length>cont)
						value=fvtex.procesarPorFunciones(o,cabeceras[cont]);
					else
						value=o.toString();
					sb.append(value+RecuperadorPropiedadedConfiguracionEntidad.getInstance().getSeparadorCSV());
				}
				else
					sb.append(RecuperadorPropiedadedConfiguracionEntidad.getInstance().getSeparadorCSV());
				cont++;
			}
			String lineaAGuardar=sb.toString();
			if(lineaAGuardar.length()!=0)
				lineaAGuardar=lineaAGuardar.substring(0, lineaAGuardar.length()-1);
			CSVHandler csv= new CSVHandler();
			csv.escribirCSV(RecuperadorPropiedadedConfiguracionEntidad.getInstance().getOutputFile(), lineaAGuardar,RecuperadorMapeoCsv.getInstancia().getCabecera(),false);		
		}
		return fvtex.getValue(RecuperadorPropiedadedConfiguracionEntidad.getInstance().getSiguientePagina(),fvtex.getArrayTipos(RecuperadorPropiedadedConfiguracionEntidad.getInstance().getSiguientePagina()),jsonObject);//((JSONObject)jsonObject.get("paging")).get("next");
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionOK(BufferedReader in, JSONHandler json, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, JSONHandler json, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionOK(BufferedReader in, JSONHandler json, String id, int responseCode)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, JSONHandler json, String id, int responseCode)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
