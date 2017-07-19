package exit.services.singletons;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Properties;

import exit.services.fileHandler.CSVHandler;
import exit.services.fileHandler.ConstantesGenerales;
import exit.services.fileHandler.DirectorioManager;
import exit.services.principal.peticiones.EPeticiones;
import exit.services.singletons.entidadesARecuperar.SFTPPropiedades;

public class RecuperadorPropiedadedConfiguracionEntidad {
	HashMap<String, String> mapPropiedades;
	private int paginaActual;
	private static RecuperadorPropiedadedConfiguracionEntidad instance;
	public static final String ACCION_CSVASERVICIO="CSVASERVICIO";
	public static final String ACCION_SERVICIOAACSV="SERVICIOAACSV";
	public static final String ACCION_SERVICIOAACSVFACEBOOK="SERVICIOAACSVFACEBOOK";
	public static final String OUTPUT_FILE_DEFAULT="salida.csv";
	public static final String OUTPUT_PATH_DEFAULT=".";
	private String outPut;
	private SFTPPropiedades sftpPropiedades=null;
    private RecuperadorPropiedadedConfiguracionEntidad(){
    	paginaActual=1;
    	mapPropiedades=new HashMap<String,String>();
        Properties props = new Properties();
        try{
		props.load(new FileReader(ConstantesGenerales.PATH_CONFIGURACION_ENTIDADES+"/"+ApuntadorDeEntidad.getInstance().getEntidadActual()+"/ConfiguracionEntidad.properties"));
		for(String key : props.stringPropertyNames()) 
			  mapPropiedades.put(key, props.getProperty(key));
        }
        catch(Exception e){
        	e.printStackTrace();
        	try(FileWriter fw= new FileWriter(DirectorioManager.getDirectorioFechaYHoraInicio("error.txt"))){
				fw.write("Error al recuperar las propierdades del fichero: ConfiguracionGeneral.properties");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }
    }
    
    void reiniciar(){
    	instance=null;
    }
    
	public void mostrarConfiguracion(){
		System.out.println("Entidad "+ ApuntadorDeEntidad.getInstance().getEntidadActual());
    	System.out.println("Directorio de busqueda: "+RecuperadorPropiedadedConfiguracionEntidad.getInstance().getPathCSVRegistros());
    	System.out.println("URL: "+RecuperadorPropiedadedConfiguracionEntidad.getInstance().getUrl());
    	System.out.println("Usuario: "+RecuperadorPropiedadedConfiguracionEntidad.getInstance().getCabecera());
	System.out.println("Acci�n: "+RecuperadorPropiedadedConfiguracionEntidad.getInstance().getAction());
    	System.out.println("Nivel de paralelismo: "+RecuperadorPropiedadedConfiguracionEntidad.getInstance().getNivelParalelismo());
    	System.out.println("Usa Proxy: "+RecuperadorPropiedadedConfiguracionEntidad.getInstance().getUsaProxy());
    	if(RecuperadorPropiedadedConfiguracionEntidad.getInstance().getUsaProxy().equalsIgnoreCase("si")){
        	System.out.println("IP Proxy: "+RecuperadorPropiedadedConfiguracionEntidad.getInstance().getIpProxy());
    		System.out.println("Puerto Proxy: "+RecuperadorPropiedadedConfiguracionEntidad.getInstance().getPuertoProxy());
    	}  	
    	System.out.println("Identificador de Atributo: "+RecuperadorPropiedadedConfiguracionEntidad.getInstance().getIdentificadorAtributo());
	}
    
    public static synchronized RecuperadorPropiedadedConfiguracionEntidad getInstance() {
    	if(instance==null)
    		instance=new RecuperadorPropiedadedConfiguracionEntidad();
    	return instance;
    }

    private String getValueMap(String key){
    	return mapPropiedades.get(key);
    }
	public String getPathCSVRegistros() {
		return getValueMap("pathCSVRegistros");
		
	}
	
	public boolean isCreateEmptyFile(){
		return getValueMap("createEmptyFile")==null?false:getValueMap("createEmptyFile").equalsIgnoreCase("true");
	}

	public String getUrl() {
		return getValueMap("url");
	}
	
	public String getSalida(){
		return getValueMap("salida");
	}

	public String getUser(){
		return getValueMap("user");
	}

	public String getPuerto(){
		return getValueMap("puerto");
	}

	public String getKeyFile(){
		return getValueMap("keyFile");
	}

	public String getMetodoPreEjecutor() {
		return getValueMap("metodoPreEjecutor");
	}

	public String getParametroPreEjecutor() {
		return getValueMap("parametroPreEjecutor");
	}
	
	public String getIterarSobre() {
		return getValueMap("iterarSobre");
	}
	
	public boolean isPaginado() {
		return getValueMap("paaginado")==null?false:getValueMap("paaginado").equalsIgnoreCase("true");
	}
	
	public String getIdIteracion() {
		return getValueMap("idIteracion");
	}
	
	public String getMetodoEjecutor() {
		return getValueMap("metodoEjecutor")==null?"ejecutorGenerico":getValueMap("metodoEjecutor");
	}

	public String getParametroEjecutor() {
		return getValueMap("parametroEjecutor");
	}
	
	public String getCabecera() {
		return getValueMap("cabecera");
	}
	
	public String getSeparadorCSV() {
		if(getValueMap("separadorCSV")==null)
			return RecuperadorPropiedadesConfiguracionGenerales.getInstance().getSeparadorCSV();
		return getValueMap("separadorCSV");
	}
	
	public String getSeparadorCSVREGEX() {
		if(getValueMap("separadorCSV")==null)
			return RecuperadorPropiedadesConfiguracionGenerales.getInstance().getSeparadorCSVREGEX();
		return "\\"+getValueMap("separadorCSV");
	}

	public int getNivelParalelismo() {
		if(getValueMap("nivelParalelismo")==null)
			return RecuperadorPropiedadesConfiguracionGenerales.getInstance().getNivelParalelismo();
		return Integer.parseInt(getValueMap("nivelParalelismo"));
	}

	public String getUsaProxy() {
		if(getValueMap("usaProxy")==null)
			return RecuperadorPropiedadesConfiguracionGenerales.getInstance().getUsaProxy();
		return getValueMap("usaProxy");
	}

	public String getIpProxy() {
		if(getValueMap("ipProxy")==null)
			RecuperadorPropiedadesConfiguracionGenerales.getInstance().getIpProxy();
		return getValueMap("ipProxy");
	}

	public String getPuertoProxy() {
		if(getValueMap("puertoProxy")==null)
			return RecuperadorPropiedadesConfiguracionGenerales.getInstance().getPuertoProxy();
		return getValueMap("puertoProxy");
	}
	public String getAction() {
		if(getValueMap("action")==null)
			return RecuperadorPropiedadesConfiguracionGenerales.getInstance().getSeparadorCSV();
		return getValueMap("action");
	}

	public String getIdentificadorAtributo() {
		if(getValueMap("separadorCSV")==null)
			return RecuperadorPropiedadesConfiguracionGenerales.getInstance().getIdentificadorAtributo();
		return getValueMap("identificadorAtributo");
	}
	
	public SFTPPropiedades getSftpPropiedades(){
		if(sftpPropiedades!=null)
			return sftpPropiedades;
		this.sftpPropiedades=new SFTPPropiedades(getValueMap("host"),getValueMap("user"), Integer.parseInt(getValueMap("puerto")), getValueMap("keyFile"));
		return sftpPropiedades;
	}
	
	public EOutputs getOutput(){
		String output=getValueMap("output");
		if(output==null)
			return EOutputs.DIRECTORIO;
		switch(output.toUpperCase()){
		case "SFTP": return EOutputs.SFTP;
		default: return EOutputs.DIRECTORIO;
		}
	}
	
	public String getOutPutPath(){
		return getValueMap("outputPath")==null?ConstantesGenerales.PATH_EJECUCION:getValueMap("outputPath");
	}
	
	public String getFiltros(){
			return getValueMap("filtros");
	}
	
	public int getPaginaActual() {
		return paginaActual;
	}	
	
	public String getSiguientePagina() {
		return getValueMap("siguientePagina");
	}	
	
	
	
	private String completarFechaConCeroAIzquierda(String value){
		return value.length()<2?"0"+value:value;
	}
	
	public String getOutputFile() {
		if(getValueMap("outputFile")==null)
			return OUTPUT_FILE_DEFAULT;
		if(outPut!=null && outPut.length()>0)
			return outPut;
		LocalDateTime ldt= LocalDateTime.now();
		outPut=getValueMap("outputFile");
		outPut=outPut.replaceAll("YY",completarFechaConCeroAIzquierda(String.valueOf(ldt.getYear())));
		outPut=outPut.replaceAll("MM",completarFechaConCeroAIzquierda(String.valueOf(ldt.getMonthValue())));
		outPut=outPut.replaceAll("DD",completarFechaConCeroAIzquierda(String.valueOf(ldt.getDayOfMonth())));
		outPut=outPut.replaceAll("HH",completarFechaConCeroAIzquierda(String.valueOf(ldt.getHour())));
		outPut=outPut.replaceAll("MI",completarFechaConCeroAIzquierda(String.valueOf(ldt.getMinute())));
		outPut=outPut.replaceAll("SS",completarFechaConCeroAIzquierda(String.valueOf(ldt.getMinute())));
		return outPut;

	}	
	
	public void incresePaginaActual() {
		paginaActual++;
	}

	public void setPaginaActual(int paginaActual) {
		this.paginaActual = paginaActual;
	}
	

	

	public boolean isBorrarDataSetAlFinalizar() {
		String borrarDataSetAlFinalizar=getValueMap("borrarDataSetAlFinalizar");
		if(borrarDataSetAlFinalizar==null)
			return RecuperadorPropiedadesConfiguracionGenerales.getInstance().isBorrarDataSetAlFinalizar();
		return borrarDataSetAlFinalizar.equalsIgnoreCase("true");
	}
	
	public static void main(String[] args) throws IOException {
	while(ApuntadorDeEntidad.getInstance().siguienteEntidad()){
		CSVHandler csv= new CSVHandler();
		csv.escribirCSV("test.txt", "aaa");	
	}
	}

}
