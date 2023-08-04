/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.modelo.pdf;

import com.agend.javaBean.BeanFichaAtePaciente;
import com.agend.modelo.ModelFichaAtencionPacNutri;
import com.agend.modelo.ModelInicioSesion;
import com.agend.modelo.ModelPersona;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RYUU
 */
//@WebServlet(name="crearPdfRptFicha", urlPatterns="/pdf_rpt_ficha")
public class CrearPdfRptFichaPac_dosPag extends HttpServlet {
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Document documento = new Document(PageSize.LEGAL);
            documento.setMargins(0,0,20,20);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(documento, baos);
            
            // -----------------------------------------------------------------------------------------------------
            //              CODIGO PARA CREAR EL PDF 
            // -----------------------------------------------------------------------------------------------------
            System.out.println("[*].REPORTE_FICHA_DEL_PACIENTE.");
            System.out.println("[*]..");
            System.out.println("[*]--------------------    CREAR_PDF_SERVLET()    --------------------");
            System.out.println("[*]..");
            System.out.println("[*].");
            // FRAGMENTO DE CODIGO PARA GUARDAR EL ARCHIVO PDF 
//                try {
//                    PdfWriter.getInstance(documento, new FileOutputStream("C:\\Empeno\\prueba_plantilla.pdf"));
//                } catch (FileNotFoundException fileNotFoundException) { // NORMALMENTE DARIA ESTE ERROR CUANDO EL ARCHIVO ESTE ABIERTO O LO ESTE USANDO OTRO PROCESO // SI YA ESTA CREADO PERO NO ESTA SIENDO UTILIZADO NO VA A DAR ERROR, VA A SOBREESCRIBIR 
//                    System.out.println("No such file was found to generate the PDF "
//                            + "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
//                }
                // ABRO EL DOCUMENTO PARA PODER IR CARGANDO CON EL CONTENIDO 
                documento.open();
                
                HttpSession sesionDatosUsuario = request.getSession(false);
//                DecimalFormat formatear = new DecimalFormat("###,###,###");
                ModelInicioSesion metodosIniSes = new ModelInicioSesion();
//                ModelPersona metodosPersona = new ModelPersona();
                ModelFichaAtencionPacNutri metodosFichaAtePacNutri = new ModelFichaAtencionPacNutri();
//                List<BeanFichaAtePaciente> listaDatos = new ArrayList<>();
                
                // PODRIA UTILIZAR LA SESION PERO COMO ESTE SERVLET PARA IMPRIMIR EMPEÑO UTILIZO TAMBIEN EN REPORTES Y AHI DEBE DE SELECCIONAR DE UNA GRILLA SI QUIERE IMPRIMIR, ENTONCES POR ESO PREGUNTO POR EL VALOR DEL CAMPO DE TEXTO, SI ESTA NULO ES PORQUE SE CARGO EL IDEMPENHO POR MEDIO DE LA SESION Y SI NO ESTA NULO ES PORQUE NO SE CARGA POR LA SESION Y POR ESO CARGO EL IDEMPENHO EN UN CAMPO DE TEXTO 
                String idFicha;
                if (String.valueOf(request.getParameter("tRFPNIF")) == null || String.valueOf(request.getParameter("tRFPNIF")).isEmpty()) {
                    System.out.println("[-] el id-ficha se encuentra null/vacio.-");
                    idFicha = "";
//                    idFactura = (String) sesionDatosUsuario.getAttribute("ID_FACTURA");
                } else {
                    idFicha = (String) request.getParameter("tRFPNIF");
                    System.out.println("[+] el id-ficha esta cargado: "+idFicha);
                }
                String idPaciente;
                if (String.valueOf(request.getParameter("tRFPNIP")) == null || String.valueOf(request.getParameter("tRFPNIP")).isEmpty()) {
                    System.out.println("[-] el id-paciente se encuentra null/vacio.-");
                    idPaciente = "";
//                    idFactura = (String) sesionDatosUsuario.getAttribute("ID_FACTURA");
                } else {
                    idPaciente = (String) request.getParameter("tRFPNIP");
                    System.out.println("[+] el id-paciente esta cargado: "+idFicha);
                }
                
                documento.addTitle("Reporte de Ficha del Paciente");
                String empresa = metodosIniSes.devolverEmpresa();
                Paragraph titulo = new Paragraph(empresa, FontFactory.getFont("arial", 16, Font.BOLD));
                titulo.setAlignment(1);
                titulo.setSpacingAfter(10f);
                documento.add(titulo);
                
            // DETALLE -------------- ----------------- ---------------- -------------- ----------------- ----------------
//                listaDatos = metodosFichaAtePacNutri.traer_datos(idFicha, sesionDatosUsuario);
//                Iterator<BeanFichaAtePaciente> iterFac = listaDatos.iterator();
//                BeanFichaAtePaciente datos = new BeanFichaAtePaciente();
//                String TXT_CLIENTE_ID="", TXT_CLIENTE_CINRO="", TXT_CLIENTE_NAME="", TXT_CLIENTE_RS="", TXT_CLIENTE_RUC="", TXT_NRO_FACTURA="", CBX_TIPO_FACTURA="", TXT_FECHA_FACTURA="", TXT_TOTAL_IVA5="", TXT_TOTAL_IVA10="", TXT_TOTAL_GRAV5="", TXT_TOTAL_GRAV10="", TXT_TOTAL_IVA="", TXT_TOTAL="";
//                List<BeanFichaAtePaciente> listaDetalle = new ArrayList<>();
                
                // CARGAR_GRILLA_DETALLE 
                List<BeanFichaAtePaciente> FICHA_DATOS = new ArrayList<>();
                FICHA_DATOS = metodosFichaAtePacNutri.traer_datos(idFicha, sesionDatosUsuario);
                Iterator<BeanFichaAtePaciente> iListDet = FICHA_DATOS.iterator();
                BeanFichaAtePaciente datos = new BeanFichaAtePaciente();
                
                // DATOS PACIENTE 
                List<String> datosPac = metodosFichaAtePacNutri.getDatosPaciente(idPaciente);
                String TXT_PAC_NOM_APE="", TXT_PAC_FECHA_NAC="", TXT_PAC_EDAD="", TXT_PAC_SEXO="", TXT_PAC_PROFESION="", TXT_PAC_TELEFONO="", TXT_PAC_DESC_CIUDAD="", TXT_PAC_CORREO="";
                if (datosPac != null) {
                    System.out.println("[+] los datos del pac esta cargando.");
                    TXT_PAC_NOM_APE = datosPac.get(0);
                    TXT_PAC_FECHA_NAC = datosPac.get(1);
                    TXT_PAC_EDAD = datosPac.get(2);
                    TXT_PAC_SEXO = metodosIniSes.getNameSexoPer(datosPac.get(3));
                    TXT_PAC_PROFESION = datosPac.get(4);
                    TXT_PAC_TELEFONO = datosPac.get(5);
                    TXT_PAC_DESC_CIUDAD = datosPac.get(6);
                    TXT_PAC_CORREO = datosPac.get(7);
                } else {
                    System.out.println("[-] los datos del pac esta vacio.");
                }
                // DATOS-BLOQUE-1
//                List<String> datosFichaCab01 = new ArrayList<>();
                String TXT_FECHA_FICHA = "", TXT_HORA_FICHA="", TXT_MOTIVO_CONSULTA="", TXT_ALIMENTOS_PREFERENCIA="", TXT_ALIMENTOS_COMES_GRL="", TXT_ALIMENTOS_NO_TOLERA="", TXT_CONSUMO_ALCOHOL="", TXT_CONSUMO_CIGARRILLO="", TXT_ALERGIAS="", TXT_ENFERMEDAD="", TXT_CIRUGIAS="", TXT_MEDICAMENTO="", TXT_OTROS_DATOS="";
                // DATOS-BLOQUE-2
//                List<String> datosFichaCab02 = new ArrayList<>();
                String CBX_REALIZA_ACTIVIDAD_FISICA="", TXT_TIPO_EJERCICIO="", TXT_EJERCICIO_FRECUENCIA="", CBX_DIGIERE_CARNE_ROJA="", CBX_ESTRENHIMIENTO="", CBX_CALAMBRES_Y_O_HORMIGUEOS="", CBX_GRASAS_SATURADAS="", CBX_CANSANCIO_FATIGA="", CBX_ZUMBIDOS_OIDO="", CBX_BUENA_DIGESTION="", CBX_HINCHAZON_ABDOMINAL="", CBX_CAIDA_DEL_CABELLO="", CBX_DUERME_PROFUNDAMENTE="", CBX_INSOMNIO="", CBX_UNHAS_QUEBRADIZAS="", CBX_DOLORES_DE_CABEZA="", CBX_MUCOSIDAD_CATARRO="", CBX_PIEL_SECA="", TXT_METABOLISMO="", CBX_ESCALA_BRISTOL="";
                // DATOS-BLOQUE-3
//                List<String> datosFichaCab03 = new ArrayList<>();
                String TXT_ESTATURA="", TXT_PORC_GRASA="",TXT_EDAD_M="",TXT_PESO="",TXT_PORC_MUSCULO="",TXT_RM="",TXT_IMC="",TXT_VISCERAL="",TXT_BALANZA="";
                // DATOS-BLOQUE-4
//                List<String> datosFichaCab04 = new ArrayList<>();
                String TXT_RESULTADOS_TEST="", TXT_SUPLEMENTACION_RECETADA="", TXT_LABORATORIO="", TXT_COMENTARIOS_GENERALES="";
                
                while(iListDet.hasNext()) {
                    datos = iListDet.next();
                    System.out.println("____WHILE____");
                    String[] FECHA_HORA_FICHA = datos.getOFPN_FECHA_FICHA_ATE().split(" ");
                    System.out.println("___________________________FECHA_FICHA:  :"+FECHA_HORA_FICHA[0]);
                    System.out.println("___________________________HORA__FICHA:  :"+FECHA_HORA_FICHA[1]);
                    // BLOQUE#1
                        TXT_FECHA_FICHA = metodosIniSes.getDatoFecha(0, FECHA_HORA_FICHA[0]);
                        TXT_HORA_FICHA = FECHA_HORA_FICHA[1];
                        TXT_MOTIVO_CONSULTA = datos.getOFPN_MOTIVO_DE_LA_CONSULTA();
                        TXT_ALIMENTOS_PREFERENCIA = datos.getOFPN_ALIMENTOS_DE_PREFERENCIA();
                        TXT_ALIMENTOS_COMES_GRL = datos.getOFPN_ALI_QUE_SUELE_COMER_GRL();
                        TXT_ALIMENTOS_NO_TOLERA = datos.getOFPN_ALIMENTOS_QUE_NO_TOLERA();
                        TXT_ALERGIAS = datos.getOFPN_ALERGIAS_A_ALGO();
                        TXT_ENFERMEDAD = datos.getOFPN_PADECE_ALGUNA_ENFERMEDAD();
                        TXT_CIRUGIAS = datos.getOFPN_CIRUGIAS();
                        TXT_MEDICAMENTO = datos.getOFPN_MEDICAMENTE_Q_E_CONSUMIENDO();
                        TXT_OTROS_DATOS = datos.getOFPN_OTROS_DATOS_A_TENER_EN_CUENTA();
                        TXT_CONSUMO_ALCOHOL = datos.getOFPN_CONSUMO_ALCOHOL();
                        TXT_CONSUMO_CIGARRILLO = datos.getOFPN_CONSUMO_CIGARRILLO();
                        // 
//                        datosFichaCab01.add(FECHA_HORA_FICHA[0]); // FECHA 
//                        datosFichaCab01.add(FECHA_HORA_FICHA[1]); // HORA 
//                        datosFichaCab01.add(datos.getOFPN_MOTIVO_DE_LA_CONSULTA());
//                        datosFichaCab01.add(datos.getOFPN_ALIMENTOS_DE_PREFERENCIA());
//                        datosFichaCab01.add(datos.getOFPN_ALI_QUE_SUELE_COMER_GRL());
//                        datosFichaCab01.add(datos.getOFPN_ALIMENTOS_QUE_NO_TOLERA());
//                        datosFichaCab01.add(datos.getOFPN_ALERGIAS_A_ALGO());
//                        datosFichaCab01.add(datos.getOFPN_PADECE_ALGUNA_ENFERMEDAD());
//                        datosFichaCab01.add(datos.getOFPN_CIRUGIAS());
//                        datosFichaCab01.add(datos.getOFPN_MEDICAMENTE_Q_E_CONSUMIENDO());
//                        datosFichaCab01.add(datos.getOFPN_OTROS_DATOS_A_TENER_EN_CUENTA());
//                        datosFichaCab01.add(datos.getOFPN_CONSUMO_ALCOHOL());
//                        datosFichaCab01.add(datos.getOFPN_CONSUMO_CIGARRILLO());
                    // BLOQUE#2
                        CBX_REALIZA_ACTIVIDAD_FISICA = metodosIniSes.getDatoSiNoAv(datos.getOFPN_REALIZA_ACTIVIDAD_FISICA());
                        TXT_TIPO_EJERCICIO = datos.getOFPN_TIPO_DE_ACTIVIDAD_FISICA();
                        TXT_EJERCICIO_FRECUENCIA = datos.getOFPN_FRECUENCIA_ACT_FISICA_SEM();
                        CBX_DIGIERE_CARNE_ROJA = metodosIniSes.getDatoSiNoAv(datos.getOFPN_DBLCR());
                        CBX_ESTRENHIMIENTO = metodosIniSes.getDatoSiNoAv(datos.getOFPN_ESTRENHIMIENTO());
                        CBX_CALAMBRES_Y_O_HORMIGUEOS = metodosIniSes.getDatoSiNoAv(datos.getOFPN_CALAMBRES_Y_HORMIGUEOS());
                        CBX_GRASAS_SATURADAS = metodosIniSes.getDatoSiNoAv(datos.getOFPN_LGSLCM());
                        CBX_CANSANCIO_FATIGA = metodosIniSes.getDatoSiNoAv(datos.getOFPN_CANSANCIO_FATIGA());
                        CBX_ZUMBIDOS_OIDO = metodosIniSes.getDatoSiNoAv(datos.getOFPN_ZUMBIDOS_EN_EL_OIDO());
                        CBX_BUENA_DIGESTION = metodosIniSes.getDatoSiNoAv(datos.getOFPN_TBDALN());
                        CBX_HINCHAZON_ABDOMINAL = metodosIniSes.getDatoSiNoAv(datos.getOFPN_HICHAZON_ABDOMINAL());
                        CBX_CAIDA_DEL_CABELLO = metodosIniSes.getDatoSiNoAv(datos.getOFPN_CAIDA_DE_CABELLO());
                        CBX_DUERME_PROFUNDAMENTE = metodosIniSes.getDatoSiNoAv(datos.getOFPN_DPALN());
                        CBX_INSOMNIO = metodosIniSes.getDatoSiNoAv(datos.getOFPN_INSOMNIO());
                        CBX_UNHAS_QUEBRADIZAS = metodosIniSes.getDatoSiNoAv(datos.getOFPN_UNHAS_QUEBRADIZAS());
                        CBX_DOLORES_DE_CABEZA = metodosIniSes.getDatoSiNoAv(datos.getOFPN_DDCCF());
                        CBX_MUCOSIDAD_CATARRO = metodosIniSes.getDatoSiNoAv(datos.getOFPN_MUCOSIDAD_Y_CATARRO());
                        CBX_PIEL_SECA = metodosIniSes.getDatoSiNoAv(datos.getOFPN_PIEL_SECA());
                        TXT_METABOLISMO = metodosIniSes.getDescMetabolismo(datos.getOFPN_TIPO_DE_METABOLISMO());
                        CBX_ESCALA_BRISTOL = metodosIniSes.getDescEscalaBristol(datos.getOFPN_TDEDBU());
                        // 
//                        datosFichaCab02.add(datos.getOFPN_REALIZA_ACTIVIDAD_FISICA());
//                        datosFichaCab02.add(datos.getOFPN_TIPO_DE_ACTIVIDAD_FISICA());
//                        datosFichaCab02.add(datos.getOFPN_FRECUENCIA_ACT_FISICA_SEM());
//                        datosFichaCab02.add(datos.getOFPN_DBLCR());
//                        datosFichaCab02.add(datos.getOFPN_ESTRENHIMIENTO());
//                        datosFichaCab02.add(datos.getOFPN_CALAMBRES_Y_HORMIGUEOS());
//                        datosFichaCab02.add(datos.getOFPN_LGSLCM());
//                        datosFichaCab02.add(datos.getOFPN_CANSANCIO_FATIGA());
//                        datosFichaCab02.add(datos.getOFPN_ZUMBIDOS_EN_EL_OIDO());
//                        datosFichaCab02.add(datos.getOFPN_TBDALN());
//                        datosFichaCab02.add(datos.getOFPN_HICHAZON_ABDOMINAL());
//                        datosFichaCab02.add(datos.getOFPN_CAIDA_DE_CABELLO());
//                        datosFichaCab02.add(datos.getOFPN_DPALN());
//                        datosFichaCab02.add(datos.getOFPN_INSOMNIO());
//                        datosFichaCab02.add(datos.getOFPN_UNHAS_QUEBRADIZAS());
//                        datosFichaCab02.add(datos.getOFPN_DDCCF());
//                        datosFichaCab02.add(datos.getOFPN_MUCOSIDAD_Y_CATARRO());
//                        datosFichaCab02.add(datos.getOFPN_PIEL_SECA());
//                        datosFichaCab02.add(datos.getOFPN_TIPO_DE_METABOLISMO());
//                        datosFichaCab02.add(datos.getOFPN_TDEDBU());
                    // BLOQUE#3
                        TXT_ESTATURA = datos.getOFPN_ESTATURA();
                        TXT_PORC_GRASA = datos.getOFPN_PORC_GRASA();
                        TXT_EDAD_M = datos.getOFPN_EDAD_METABOLICA();
                        TXT_PESO = datos.getOFPN_PESO();
                        TXT_PORC_MUSCULO = datos.getOFPN_PORC_MUSCULO();
                        TXT_RM = datos.getOFPN_RM();
                        TXT_IMC = datos.getOFPN_IMC();
                        TXT_VISCERAL = datos.getOFPN_VISCERAL();
                        TXT_BALANZA = datos.getOFPN_TIPO_DE_BALANZA();
                        //
//                        datosFichaCab03.add(datos.getOFPN_ESTATURA());
//                        datosFichaCab03.add(datos.getOFPN_PORC_GRASA());
//                        datosFichaCab03.add(datos.getOFPN_EDAD_METABOLICA());
//                        datosFichaCab03.add(datos.getOFPN_PESO());
//                        datosFichaCab03.add(datos.getOFPN_PORC_MUSCULO());
//                        datosFichaCab03.add(datos.getOFPN_RM());
//                        datosFichaCab03.add(datos.getOFPN_IMC());
//                        datosFichaCab03.add(datos.getOFPN_VISCERAL());
//                        datosFichaCab03.add(datos.getOFPN_TIPO_DE_BALANZA());
                    // BLOQUE#4
                        TXT_RESULTADOS_TEST = datos.getOFPN_RESULTADOS_TEST_BIORESONANCIA();
                        TXT_SUPLEMENTACION_RECETADA = datos.getOFPN_SUPLEMENTACION_RECETADA();
                        TXT_LABORATORIO = datos.getOFPN_LABORATORIO();
                        TXT_COMENTARIOS_GENERALES = datos.getOFPN_COMENTARIOS_GENERALES();
                        //
//                        datosFichaCab04.add(datos.getOFPN_RESULTADOS_TEST_BIORESONANCIA());
//                        datosFichaCab04.add(datos.getOFPN_SUPLEMENTACION_RECETADA());
//                        datosFichaCab04.add(datos.getOFPN_LABORATORIO());
//                        datosFichaCab04.add(datos.getOFPN_COMENTARIOS_GENERALES());
                } // end while 
                
//                // OBTENGO LOS DATOS DEL CLIENTE 
//                BeanPersona datosCliente = new BeanPersona();
//                datosCliente = metodosPersona.datosBasicosPersona(datosCliente, TXT_CLIENTE_ID);
//                TXT_CLIENTE_CINRO = datosCliente.getRP_CINRO();
//                TXT_CLIENTE_NAME = datosCliente.getRP_NOMBRE()+" "+datosCliente.getRP_APELLIDO();
//                TXT_CLIENTE_RS = datosCliente.getRP_RAZON_SOCIAL();
//                TXT_CLIENTE_RUC = datosCliente.getRP_RUC();
                
                
                
                // SUBTITULO DE LA PAGINA 
                Paragraph subtitulo = new Paragraph("Detalle de la Ficha Nº "+idFicha+".", FontFactory.getFont("arial", 15, Font.BOLD));
                subtitulo.setAlignment(1);
                subtitulo.setSpacingAfter(10f);
                documento.add(subtitulo);
                
                // variable con valor para estandarizar cuando quiera cambiar el valor 
                float valuePaddingTop = 10f;
                float valuePaddingRight = 20f;
                int fontSize10 = 10;
                int fontSize11 = 11;
                
                // BLOQUE#01.-
                PdfPTable tablaBloque01 = new PdfPTable(2);
                tablaBloque01.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                tablaBloque01.setTotalWidth(550);
                float[] medidaCeldaCab = {250, 250};
                tablaBloque01.setWidths(medidaCeldaCab);
                tablaBloque01.setHorizontalAlignment(Element.ALIGN_LEFT);
                // label NAME_PACIENTE 
                PdfPCell lblPaciente = new PdfPCell(new Phrase("Paciente: ", FontFactory.getFont("arial", fontSize11, Font.BOLD)));
                lblPaciente.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblPaciente.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblPaciente.setPaddingTop(valuePaddingTop);
                lblPaciente.setPaddingRight(valuePaddingRight);
                lblPaciente.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(lblPaciente);
                // value NAME_PACIENTE 
                PdfPCell valuePaciente = new PdfPCell(new Phrase(TXT_PAC_NOM_APE, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valuePaciente.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valuePaciente.setPaddingTop(valuePaddingTop);
                valuePaciente.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(valuePaciente);
                // label FECHA/HORA 
                PdfPCell lblFechaHora = new PdfPCell(new Phrase("Fecha y Hora de la Ficha: ", FontFactory.getFont("arial", fontSize11, Font.BOLD)));
                lblFechaHora.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblFechaHora.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblFechaHora.setPaddingTop(valuePaddingTop);
                lblFechaHora.setPaddingRight(valuePaddingRight);
                lblFechaHora.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(lblFechaHora);
                // value FECHA/HORA 
                PdfPCell valueFechaHora = new PdfPCell(new Phrase((TXT_FECHA_FICHA+" "+TXT_HORA_FICHA), FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueFechaHora.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueFechaHora.setPaddingTop(valuePaddingTop);
                valueFechaHora.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(valueFechaHora);
                // label MOTIVO_CONSULTA 
                PdfPCell lblMotivoConsulta = new PdfPCell(new Phrase("Motivo de la Consulta: ", FontFactory.getFont("arial", fontSize11, Font.BOLD)));
                lblMotivoConsulta.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblMotivoConsulta.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblMotivoConsulta.setPaddingTop(valuePaddingTop);
                lblMotivoConsulta.setPaddingRight(valuePaddingRight);
                lblMotivoConsulta.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(lblMotivoConsulta);
                // value MOTIVO_CONSULTA 
                PdfPCell valueMotivoConsulta = new PdfPCell(new Phrase(TXT_MOTIVO_CONSULTA, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueMotivoConsulta.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueMotivoConsulta.setPaddingTop(valuePaddingTop);
                valueMotivoConsulta.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(valueMotivoConsulta);
                // label ALIMENTOS_DE_PREFERENCIA  
                PdfPCell lblAlimentosPref = new PdfPCell(new Phrase("Alimentos de Preferencia: ", FontFactory.getFont("arial", fontSize11, Font.BOLD)));
                lblAlimentosPref.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblAlimentosPref.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblAlimentosPref.setPaddingTop(valuePaddingTop);
                lblAlimentosPref.setPaddingRight(valuePaddingRight);
                lblAlimentosPref.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(lblAlimentosPref);
                // value ALIMENTOS_DE_PREFERENCIA 
                PdfPCell valueAlimentosPref = new PdfPCell(new Phrase(TXT_ALIMENTOS_PREFERENCIA, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueAlimentosPref.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueAlimentosPref.setPaddingTop(valuePaddingTop);
                valueAlimentosPref.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(valueAlimentosPref);
                // label QUE_SUELE_COMER_GRL 
                PdfPCell lblQueSueleComerGrl = new PdfPCell(new Phrase("Que suele comer generalmente: ", FontFactory.getFont("arial", fontSize11, Font.BOLD)));
                lblQueSueleComerGrl.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblQueSueleComerGrl.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblQueSueleComerGrl.setPaddingTop(valuePaddingTop);
                lblQueSueleComerGrl.setPaddingRight(valuePaddingRight);
                lblQueSueleComerGrl.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(lblQueSueleComerGrl);
                // value QUE_SUELE_COMER_GRL 
                PdfPCell valueQueSueleComerGrl = new PdfPCell(new Phrase(TXT_ALIMENTOS_COMES_GRL, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueQueSueleComerGrl.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueQueSueleComerGrl.setPaddingTop(valuePaddingTop);
                valueQueSueleComerGrl.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(valueQueSueleComerGrl);
                // label ALIMENTOS_NO_TOLERA 
                PdfPCell lblAlimentosNoTolera = new PdfPCell(new Phrase("Alimentos que no tolera o no le guste: ", FontFactory.getFont("arial", fontSize11, Font.BOLD)));
                lblAlimentosNoTolera.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblAlimentosNoTolera.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblAlimentosNoTolera.setPaddingTop(valuePaddingTop);
                lblAlimentosNoTolera.setPaddingRight(valuePaddingRight);
                lblAlimentosNoTolera.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(lblAlimentosNoTolera);
                // value ALIMENTOS_NO_TOLERA
                PdfPCell valueAlimentosNoTolera = new PdfPCell(new Phrase(TXT_ALIMENTOS_NO_TOLERA, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueAlimentosNoTolera.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueAlimentosNoTolera.setPaddingTop(valuePaddingTop);
                valueAlimentosNoTolera.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(valueAlimentosNoTolera);
                // label RUC_CLIENTE 
                PdfPCell lblRucCliente = new PdfPCell(new Phrase("Consumo Alcohol: ", FontFactory.getFont("arial", fontSize11, Font.BOLD)));
                lblRucCliente.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblRucCliente.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblRucCliente.setPaddingTop(valuePaddingTop);
                lblRucCliente.setPaddingRight(valuePaddingRight);
                lblRucCliente.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(lblRucCliente);
                // value RUC_CLIENTE 
                PdfPCell valueRucCliente = new PdfPCell(new Phrase(TXT_CONSUMO_ALCOHOL, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueRucCliente.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueRucCliente.setPaddingTop(valuePaddingTop);
                valueRucCliente.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(valueRucCliente);
                // label CONSUMO_CIGARRILLO 
                PdfPCell lblConsumoCigarrillo = new PdfPCell(new Phrase("Consumo Cigarrillo: ", FontFactory.getFont("arial", fontSize11, Font.BOLD)));
                lblConsumoCigarrillo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblConsumoCigarrillo.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblConsumoCigarrillo.setPaddingTop(valuePaddingTop);
                lblConsumoCigarrillo.setPaddingRight(valuePaddingRight);
                lblConsumoCigarrillo.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(lblConsumoCigarrillo);
                // value CONSUMO_CIGARRILLO 
                PdfPCell valueConsumoCigarrillo = new PdfPCell(new Phrase(TXT_CONSUMO_CIGARRILLO, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueConsumoCigarrillo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueConsumoCigarrillo.setPaddingTop(valuePaddingTop);
                valueConsumoCigarrillo.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(valueConsumoCigarrillo);
                // label ALERGIAS_A_ALGO 
                PdfPCell lblAlergias = new PdfPCell(new Phrase("Alergias a algo: ", FontFactory.getFont("arial", fontSize11, Font.BOLD)));
                lblAlergias.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblAlergias.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblAlergias.setPaddingTop(valuePaddingTop);
                lblAlergias.setPaddingRight(valuePaddingRight);
                lblAlergias.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(lblAlergias);
                // value ALERGIAS_A_ALGO 
                PdfPCell valueAlergias = new PdfPCell(new Phrase(TXT_ALERGIAS, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueAlergias.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueAlergias.setPaddingTop(valuePaddingTop);
                valueAlergias.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(valueAlergias);
                // label PADECE_ENFERMEDAD 
                PdfPCell lblEnfermedad = new PdfPCell(new Phrase("¿Padece alguna enfermedad? ¿Cuál?: ", FontFactory.getFont("arial", fontSize11, Font.BOLD)));
                lblEnfermedad.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblEnfermedad.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblEnfermedad.setPaddingTop(valuePaddingTop);
                lblEnfermedad.setPaddingRight(valuePaddingRight);
                lblEnfermedad.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(lblEnfermedad);
                // value PADECE_ENFERMEDAD 
                PdfPCell valueEnfermedad = new PdfPCell(new Phrase(TXT_ENFERMEDAD, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueEnfermedad.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueEnfermedad.setPaddingTop(valuePaddingTop);
                valueEnfermedad.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(valueEnfermedad);
                // label CIRUGIAS 
                PdfPCell lblCirugias = new PdfPCell(new Phrase("Cirugías: ", FontFactory.getFont("arial", fontSize11, Font.BOLD)));
                lblCirugias.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblCirugias.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblCirugias.setPaddingTop(valuePaddingTop);
                lblCirugias.setPaddingRight(valuePaddingRight);
                lblCirugias.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(lblCirugias);
                // value CIRUGIAS 
                PdfPCell valueCirugias = new PdfPCell(new Phrase(TXT_CIRUGIAS, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueCirugias.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueCirugias.setPaddingTop(valuePaddingTop);
                valueCirugias.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(valueCirugias);
                // label MEDICAMENTO/SUPLEMENTACION_QUE_ESTE_CONSUMIENDO 
                PdfPCell lblMedicamento = new PdfPCell(new Phrase("Medicamento y/o suplementación que este consumiendo: ", FontFactory.getFont("arial", fontSize11, Font.BOLD)));
                lblMedicamento.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblMedicamento.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblMedicamento.setPaddingTop(valuePaddingTop);
                lblMedicamento.setPaddingRight(valuePaddingRight);
                lblMedicamento.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(lblMedicamento);
                // value MEDICAMENTO/SUPLEMENTACION_QUE_ESTE_CONSUMIENDO 
                PdfPCell valueMedicamento = new PdfPCell(new Phrase(TXT_MEDICAMENTO, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueMedicamento.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueMedicamento.setPaddingTop(valuePaddingTop);
                valueMedicamento.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(valueMedicamento);
                // label OTROS_DATOS_A_TENER_EN_CUENTA 
                PdfPCell lblOtrosDatos = new PdfPCell(new Phrase("Otros datos a tener en cuenta: ", FontFactory.getFont("arial", fontSize11, Font.BOLD)));
                lblOtrosDatos.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblOtrosDatos.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblOtrosDatos.setPaddingTop(valuePaddingTop);
                lblOtrosDatos.setPaddingRight(valuePaddingRight);
                lblOtrosDatos.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(lblOtrosDatos);
                // value OTROS_DATOS 
                PdfPCell valueOtrosDatos = new PdfPCell(new Phrase(TXT_OTROS_DATOS, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueOtrosDatos.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueOtrosDatos.setPaddingTop(valuePaddingTop);
                valueOtrosDatos.setBorder(Rectangle.NO_BORDER);
                tablaBloque01.addCell(valueOtrosDatos);
                // CARGO LA TABLA AL DOCUMENTO.-
                tablaBloque01.setSpacingBefore(15);
                documento.add(tablaBloque01);
                
                
                // new page
//                documento.newPage();
                // BLOQUE#02 -----------------------------------------------------------------------------------------------------------------------------
                PdfPTable tablaBloque02 = new PdfPTable(2);
                tablaBloque02.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                tablaBloque02.setTotalWidth(550);
                float[] medidaCeldaB02 = {250, 250};
                tablaBloque02.setWidths(medidaCeldaB02);
                tablaBloque02.setHorizontalAlignment(Element.ALIGN_LEFT);
                // label REALIZA_ACTIVIDAD_FISICA 
                PdfPCell lblRealizaActFisica = new PdfPCell(new Phrase("Realiza Actividad Física: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblRealizaActFisica.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblRealizaActFisica.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblRealizaActFisica.setPaddingTop(valuePaddingTop);
                lblRealizaActFisica.setPaddingRight(valuePaddingRight);
                lblRealizaActFisica.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(lblRealizaActFisica);
                // value REALIZA_ACTIVIDAD_FISICA 
                PdfPCell valueRealizaActFisica = new PdfPCell(new Phrase(CBX_REALIZA_ACTIVIDAD_FISICA, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueRealizaActFisica.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueRealizaActFisica.setPaddingTop(valuePaddingTop);
                valueRealizaActFisica.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(valueRealizaActFisica);
                // label TIPO_DE_EJERCICIO 
                PdfPCell lblTipoEjercicio = new PdfPCell(new Phrase("Tipo de ejercicio o deporte: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblTipoEjercicio.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblTipoEjercicio.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblTipoEjercicio.setPaddingTop(valuePaddingTop);
                lblTipoEjercicio.setPaddingRight(valuePaddingRight);
                lblTipoEjercicio.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(lblTipoEjercicio);
                // value TIPO_DE_EJERCICIO 
                PdfPCell valueTipoEjercicio = new PdfPCell(new Phrase(TXT_TIPO_EJERCICIO, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueTipoEjercicio.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueTipoEjercicio.setPaddingTop(valuePaddingTop);
                valueTipoEjercicio.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(valueTipoEjercicio);
                // label FRECUENCIA 
                PdfPCell lblFrecuencia = new PdfPCell(new Phrase("Frecuencia: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblFrecuencia.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblFrecuencia.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblFrecuencia.setPaddingTop(valuePaddingTop);
                lblFrecuencia.setPaddingRight(valuePaddingRight);
                lblFrecuencia.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(lblFrecuencia);
                // value FRECUENCIA 
                PdfPCell valueFrecuencia = new PdfPCell(new Phrase(TXT_EJERCICIO_FRECUENCIA, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueFrecuencia.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueFrecuencia.setPaddingTop(valuePaddingTop);
                valueFrecuencia.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(valueFrecuencia);
                // label DIGIERE_CARNE_ROJA  
                PdfPCell lblDigiereCarneRoja = new PdfPCell(new Phrase("Digiere bien las carnes rojas: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblDigiereCarneRoja.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblDigiereCarneRoja.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblDigiereCarneRoja.setPaddingTop(valuePaddingTop);
                lblDigiereCarneRoja.setPaddingRight(valuePaddingRight);
                lblDigiereCarneRoja.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(lblDigiereCarneRoja);
                // value DIGIERE_CARNE_ROJA 
                PdfPCell valueDigiereCarneRoja = new PdfPCell(new Phrase(CBX_DIGIERE_CARNE_ROJA, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueDigiereCarneRoja.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueDigiereCarneRoja.setPaddingTop(valuePaddingTop);
                valueDigiereCarneRoja.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(valueDigiereCarneRoja);
                // label ESTRENHIMIENTO 
                PdfPCell lblEstrenhimiento = new PdfPCell(new Phrase("Estreñimiento: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblEstrenhimiento.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblEstrenhimiento.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblEstrenhimiento.setPaddingTop(valuePaddingTop);
                lblEstrenhimiento.setPaddingRight(valuePaddingRight);
                lblEstrenhimiento.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(lblEstrenhimiento);
                // value ESTRENHIMIENTO 
                PdfPCell valueEstrenhimiento = new PdfPCell(new Phrase(CBX_ESTRENHIMIENTO, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueEstrenhimiento.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueEstrenhimiento.setPaddingTop(valuePaddingTop);
                valueEstrenhimiento.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(valueEstrenhimiento);
                // label CALAMBRES_Y_O_HORMIGUEOS 
                PdfPCell lblCalambresHormigueos = new PdfPCell(new Phrase("Calambres y/o Hormigueos: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblCalambresHormigueos.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblCalambresHormigueos.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblCalambresHormigueos.setPaddingTop(valuePaddingTop);
                lblCalambresHormigueos.setPaddingRight(valuePaddingRight);
                lblCalambresHormigueos.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(lblCalambresHormigueos);
                // value CALAMBRES_Y_O_HORMIGUEOS
                PdfPCell valueCalambreHormigueos = new PdfPCell(new Phrase(CBX_CALAMBRES_Y_O_HORMIGUEOS, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueCalambreHormigueos.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueCalambreHormigueos.setPaddingTop(valuePaddingTop);
                valueCalambreHormigueos.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(valueCalambreHormigueos);
                // label LAS_GRASAS_SATURADAS_LE_CAE_MAL 
                PdfPCell lblGrasasSaturadasCaeMal = new PdfPCell(new Phrase("Las Grasas saturadas le cae mal: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblGrasasSaturadasCaeMal.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblGrasasSaturadasCaeMal.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblGrasasSaturadasCaeMal.setPaddingTop(valuePaddingTop);
                lblGrasasSaturadasCaeMal.setPaddingRight(valuePaddingRight);
                lblGrasasSaturadasCaeMal.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(lblGrasasSaturadasCaeMal);
                // value LAS_GRASAS_SATURADAS_LE_CAE_MAL 
                PdfPCell valueGrasasSaturadasCaeMal = new PdfPCell(new Phrase(CBX_GRASAS_SATURADAS, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueGrasasSaturadasCaeMal.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueGrasasSaturadasCaeMal.setPaddingTop(valuePaddingTop);
                valueGrasasSaturadasCaeMal.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(valueGrasasSaturadasCaeMal);
                // label CANSANCIO_Y_FATIGA 
                PdfPCell lblCansancioFatiga = new PdfPCell(new Phrase("Cansancio y Fatiga: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblCansancioFatiga.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblCansancioFatiga.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblCansancioFatiga.setPaddingTop(valuePaddingTop);
                lblCansancioFatiga.setPaddingRight(valuePaddingRight);
                lblCansancioFatiga.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(lblCansancioFatiga);
                // value CANSANCIO_Y_FATIGA 
                PdfPCell valueCansancioFatiga = new PdfPCell(new Phrase(CBX_CANSANCIO_FATIGA, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueCansancioFatiga.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueCansancioFatiga.setPaddingTop(valuePaddingTop);
                valueCansancioFatiga.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(valueCansancioFatiga);
                // label ZUMBIDOS_OIDO 
                PdfPCell lblZumbidosEnElOido = new PdfPCell(new Phrase("Zumbidos en el oído: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblZumbidosEnElOido.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblZumbidosEnElOido.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblZumbidosEnElOido.setPaddingTop(valuePaddingTop);
                lblZumbidosEnElOido.setPaddingRight(valuePaddingRight);
                lblZumbidosEnElOido.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(lblZumbidosEnElOido);
                // value ZUMBIDOS_OIDO 
                PdfPCell valueZumbidosEnElOido = new PdfPCell(new Phrase(CBX_ZUMBIDOS_OIDO, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueZumbidosEnElOido.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueZumbidosEnElOido.setPaddingTop(valuePaddingTop);
                valueZumbidosEnElOido.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(valueZumbidosEnElOido);
                // label TIENE_BUENA_DIGESTION 
                PdfPCell lblTieneBuenaDigestion = new PdfPCell(new Phrase("Tiene buena digestión a la noche: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblTieneBuenaDigestion.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblTieneBuenaDigestion.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblTieneBuenaDigestion.setPaddingTop(valuePaddingTop);
                lblTieneBuenaDigestion.setPaddingRight(valuePaddingRight);
                lblTieneBuenaDigestion.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(lblTieneBuenaDigestion);
                // value TIENE_BUENA_DIGESTION 
                PdfPCell valueTieneBuenaDigestion = new PdfPCell(new Phrase(CBX_BUENA_DIGESTION, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueTieneBuenaDigestion.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueTieneBuenaDigestion.setPaddingTop(valuePaddingTop);
                valueTieneBuenaDigestion.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(valueTieneBuenaDigestion);
                // label HINCHAZON_ABDOMINAL 
                PdfPCell lblHinchazonAbdominal = new PdfPCell(new Phrase("Hinchazón abdominal: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblHinchazonAbdominal.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblHinchazonAbdominal.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblHinchazonAbdominal.setPaddingTop(valuePaddingTop);
                lblHinchazonAbdominal.setPaddingRight(valuePaddingRight);
                lblHinchazonAbdominal.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(lblHinchazonAbdominal);
                // value HINCHAZON_ABDOMINAL 
                PdfPCell valueHinchanzonAbdominal = new PdfPCell(new Phrase(CBX_HINCHAZON_ABDOMINAL, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueHinchanzonAbdominal.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueHinchanzonAbdominal.setPaddingTop(valuePaddingTop);
                valueHinchanzonAbdominal.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(valueHinchanzonAbdominal);
                // label CAIDA_DEL_CABELLO 
                PdfPCell lblCaidaDelCabello = new PdfPCell(new Phrase("Caída del cabello: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblCaidaDelCabello.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblCaidaDelCabello.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblCaidaDelCabello.setPaddingTop(valuePaddingTop);
                lblCaidaDelCabello.setPaddingRight(valuePaddingRight);
                lblCaidaDelCabello.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(lblCaidaDelCabello);
                // value CAIDA_DEL_CABELLO 
                PdfPCell valueCaidaDelCabello = new PdfPCell(new Phrase(CBX_CAIDA_DEL_CABELLO, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueCaidaDelCabello.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueCaidaDelCabello.setPaddingTop(valuePaddingTop);
                valueCaidaDelCabello.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(valueCaidaDelCabello);
                // label DUERME_PROFUNDAMENTE 
                PdfPCell lblDuermeProfundamente = new PdfPCell(new Phrase("Duerme profundamente: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblDuermeProfundamente.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblDuermeProfundamente.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblDuermeProfundamente.setPaddingTop(valuePaddingTop);
                lblDuermeProfundamente.setPaddingRight(valuePaddingRight);
                lblDuermeProfundamente.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(lblDuermeProfundamente);
                // value DUERME_PROFUNDAMENTE 
                PdfPCell valueDuermeProfundamente = new PdfPCell(new Phrase(CBX_DUERME_PROFUNDAMENTE, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueDuermeProfundamente.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueDuermeProfundamente.setPaddingTop(valuePaddingTop);
                valueDuermeProfundamente.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(valueDuermeProfundamente);
                // label UNHAS_QUEBRADIZAS 
                PdfPCell lblUnhasQuebradizas = new PdfPCell(new Phrase("Uñas Quebradizas: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblUnhasQuebradizas.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblUnhasQuebradizas.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblUnhasQuebradizas.setPaddingTop(valuePaddingTop);
                lblUnhasQuebradizas.setPaddingRight(valuePaddingRight);
                lblUnhasQuebradizas.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(lblUnhasQuebradizas);
                // value UNHAS_QUEBRADIZAS 
                PdfPCell valueUnhasQuebradizas = new PdfPCell(new Phrase(CBX_UNHAS_QUEBRADIZAS, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueUnhasQuebradizas.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueUnhasQuebradizas.setPaddingTop(valuePaddingTop);
                valueUnhasQuebradizas.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(valueUnhasQuebradizas);
                // label DOLORES_DE_CABEZA_FRECUENTES 
                PdfPCell lblDoloresCabFrec = new PdfPCell(new Phrase("Dolores de cabeza con Frecuencia: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblDoloresCabFrec.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblDoloresCabFrec.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblDoloresCabFrec.setPaddingTop(valuePaddingTop);
                lblDoloresCabFrec.setPaddingRight(valuePaddingRight);
                lblDoloresCabFrec.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(lblDoloresCabFrec);
                // value DOLORES_DE_CABEZA_FRECUENTES 
                PdfPCell valueDoloresCabFrec = new PdfPCell(new Phrase(CBX_DOLORES_DE_CABEZA, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueDoloresCabFrec.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueDoloresCabFrec.setPaddingTop(valuePaddingTop);
                valueDoloresCabFrec.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(valueDoloresCabFrec);
                // label MUCOSIDAD_CATARRO 
                PdfPCell lblMucosidadCatarro = new PdfPCell(new Phrase("Mucosidad y Catarro: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblMucosidadCatarro.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblMucosidadCatarro.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblMucosidadCatarro.setPaddingTop(valuePaddingTop);
                lblMucosidadCatarro.setPaddingRight(valuePaddingRight);
                lblMucosidadCatarro.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(lblMucosidadCatarro);
                // value MUCOSIDAD_CATARRO 
                PdfPCell valueMucosidadCatarro = new PdfPCell(new Phrase(CBX_MUCOSIDAD_CATARRO, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueMucosidadCatarro.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueMucosidadCatarro.setPaddingTop(valuePaddingTop);
                valueMucosidadCatarro.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(valueMucosidadCatarro);
                // label PIEL_SECA 
                PdfPCell lblPielSeca = new PdfPCell(new Phrase("Piel Seca: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblPielSeca.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblPielSeca.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblPielSeca.setPaddingTop(valuePaddingTop);
                lblPielSeca.setPaddingRight(valuePaddingRight);
                lblPielSeca.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(lblPielSeca);
                // value PIEL_SECA 
                PdfPCell valuePielSeca = new PdfPCell(new Phrase(CBX_PIEL_SECA, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valuePielSeca.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valuePielSeca.setPaddingTop(valuePaddingTop);
                valuePielSeca.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(valuePielSeca);
                // label METABOLISMO 
                PdfPCell lblMetabolismo = new PdfPCell(new Phrase("Metabolismo: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblMetabolismo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblMetabolismo.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblMetabolismo.setPaddingTop(valuePaddingTop);
                lblMetabolismo.setPaddingRight(valuePaddingRight);
                lblMetabolismo.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(lblMetabolismo);
                // value METABOLISMO 
                PdfPCell valueMetabolismo = new PdfPCell(new Phrase(TXT_METABOLISMO, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueMetabolismo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueMetabolismo.setPaddingTop(valuePaddingTop);
                valueMetabolismo.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(valueMetabolismo);
                // label TIPO_DE_ESCALA_BRISTOL 
                PdfPCell lblTipoEscalaBristol = new PdfPCell(new Phrase("Tipo de \"Escala de Bristol\" Usual: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblTipoEscalaBristol.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblTipoEscalaBristol.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblTipoEscalaBristol.setPaddingTop(valuePaddingTop);
                lblTipoEscalaBristol.setPaddingRight(valuePaddingRight);
                lblTipoEscalaBristol.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(lblTipoEscalaBristol);
                // value TIPO_DE_ESCALA_BRISTOL 
                PdfPCell valueTipoEscalaBristol = new PdfPCell(new Phrase(CBX_ESCALA_BRISTOL, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueTipoEscalaBristol.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueTipoEscalaBristol.setPaddingTop(valuePaddingTop);
                valueTipoEscalaBristol.setBorder(Rectangle.NO_BORDER);
                tablaBloque02.addCell(valueTipoEscalaBristol);
                // CARGO LA TABLA AL DOCUMENTO.-
                tablaBloque02.setSpacingBefore(15);
                documento.add(tablaBloque02);
                
                
                // new page
                documento.newPage();
                // BLOQUE#03 -----------------------------------------------------------------------------------------------------------------------------
                PdfPTable tablaBloque03 = new PdfPTable(4);
//                PdfPTable tablaBloque03 = new PdfPTable(2);
                tablaBloque03.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                tablaBloque03.setTotalWidth(550);
                float[] medidaCeldaB03 = {170, 100, 170, 100};
//                float[] medidaCeldaB03 = {250, 250};
                tablaBloque03.setWidths(medidaCeldaB03);
                tablaBloque03.setHorizontalAlignment(Element.ALIGN_LEFT);
                // label ESTATURA 
                PdfPCell lblEstatura = new PdfPCell(new Phrase("Estatura: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblEstatura.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblEstatura.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblEstatura.setPaddingTop(valuePaddingTop);
                lblEstatura.setPaddingRight(valuePaddingRight);
                lblEstatura.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(lblEstatura);
                // value ESTATURA 
                PdfPCell valueEstatura = new PdfPCell(new Phrase(TXT_ESTATURA, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueEstatura.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueEstatura.setPaddingTop(valuePaddingTop);
                valueEstatura.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(valueEstatura);
                // label PESO 
                PdfPCell lblPeso = new PdfPCell(new Phrase("Peso: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblPeso.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblPeso.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblPeso.setPaddingTop(valuePaddingTop);
                lblPeso.setPaddingRight(valuePaddingRight);
                lblPeso.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(lblPeso);
                // value PESO 
                PdfPCell valuePeso = new PdfPCell(new Phrase(TXT_PESO, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valuePeso.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valuePeso.setPaddingTop(valuePaddingTop);
                valuePeso.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(valuePeso);
                // label PORCENTAJE_GRASA 
                PdfPCell lblPorcGrasa = new PdfPCell(new Phrase("Porc. Grasa: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblPorcGrasa.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblPorcGrasa.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblPorcGrasa.setPaddingTop(valuePaddingTop);
                lblPorcGrasa.setPaddingRight(valuePaddingRight);
                lblPorcGrasa.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(lblPorcGrasa);
                // value PORCENTAJE_GRASA 
                PdfPCell valuePorcGrasa = new PdfPCell(new Phrase(TXT_PORC_GRASA, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valuePorcGrasa.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valuePorcGrasa.setPaddingTop(valuePaddingTop);
                valuePorcGrasa.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(valuePorcGrasa);
                // label PORCENTAJE_MUSCULO  
                PdfPCell lblPorcMusculo = new PdfPCell(new Phrase("Porc. Músculo: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblPorcMusculo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblPorcMusculo.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblPorcMusculo.setPaddingTop(valuePaddingTop);
                lblPorcMusculo.setPaddingRight(valuePaddingRight);
                lblPorcMusculo.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(lblPorcMusculo);
                // value PORCENTAJE_MUSCULO 
                PdfPCell valuePorcMusculo = new PdfPCell(new Phrase(TXT_PORC_MUSCULO, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valuePorcMusculo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valuePorcMusculo.setPaddingTop(valuePaddingTop);
                valuePorcMusculo.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(valuePorcMusculo);
                // label IMC 
                PdfPCell lblImc = new PdfPCell(new Phrase("IMC: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblImc.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblImc.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblImc.setPaddingTop(valuePaddingTop);
                lblImc.setPaddingRight(valuePaddingRight);
                lblImc.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(lblImc);
                // value IMC 
                PdfPCell valueImc = new PdfPCell(new Phrase(TXT_IMC, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueImc.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueImc.setPaddingTop(valuePaddingTop);
                valueImc.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(valueImc);
                // label EDAD_METABOLICA 
                PdfPCell lblEdadM = new PdfPCell(new Phrase("Edad M.: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblEdadM.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblEdadM.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblEdadM.setPaddingTop(valuePaddingTop);
                lblEdadM.setPaddingRight(valuePaddingRight);
                lblEdadM.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(lblEdadM);
                // value EDAD_METABOLICA
                PdfPCell valueEdadM = new PdfPCell(new Phrase(TXT_EDAD_M, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueEdadM.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueEdadM.setPaddingTop(valuePaddingTop);
                valueEdadM.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(valueEdadM);
                // label RM 
                PdfPCell lblRm = new PdfPCell(new Phrase("RM: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblRm.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblRm.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblRm.setPaddingTop(valuePaddingTop);
                lblRm.setPaddingRight(valuePaddingRight);
                lblRm.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(lblRm);
                // value RM 
                PdfPCell valueRm = new PdfPCell(new Phrase(TXT_RM, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueRm.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueRm.setPaddingTop(valuePaddingTop);
                valueRm.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(valueRm);
                // label VISCERAL 
                PdfPCell lblVisceral = new PdfPCell(new Phrase("Visceral: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblVisceral.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblVisceral.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblVisceral.setPaddingTop(valuePaddingTop);
                lblVisceral.setPaddingRight(valuePaddingRight);
                lblVisceral.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(lblVisceral);
                // value VISCERAL 
                PdfPCell valueVisceral = new PdfPCell(new Phrase(TXT_VISCERAL, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueVisceral.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueVisceral.setPaddingTop(valuePaddingTop);
                valueVisceral.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(valueVisceral);
                // label BALANZA 
                PdfPCell lblBalanza = new PdfPCell(new Phrase("Balanza: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblBalanza.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblBalanza.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblBalanza.setPaddingTop(valuePaddingTop);
                lblBalanza.setPaddingRight(valuePaddingRight);
                lblBalanza.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(lblBalanza);
                // value BALANZA 
                PdfPCell valueBalanza = new PdfPCell(new Phrase(TXT_BALANZA, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueBalanza.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueBalanza.setPaddingTop(valuePaddingTop);
                valueBalanza.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(valueBalanza);
                // label VACIO :PARA MOSTRAR EL DATO DE "BALANZA" PORQUE NO ME MUESTRA EL DATO DE UNA COLUMNA SI LA OTRA NO EXISTE... 
                PdfPCell lblVacio = new PdfPCell(new Phrase(" ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblVacio.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblVacio.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblVacio.setPaddingTop(valuePaddingTop);
                lblVacio.setPaddingRight(valuePaddingRight);
                lblVacio.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(lblVacio);
                // value VACIO 
                PdfPCell valueVacio = new PdfPCell(new Phrase("", FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueVacio.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueVacio.setPaddingTop(valuePaddingTop);
                valueVacio.setBorder(Rectangle.NO_BORDER);
                tablaBloque03.addCell(valueVacio);
                // CARGO LA TABLA AL DOCUMENTO.-
                tablaBloque03.setSpacingBefore(15);
                documento.add(tablaBloque03);
                
                
                // new page
//                documento.newPage();
                // BLOQUE#03 -----------------------------------------------------------------------------------------------------------------------------
                PdfPTable tablaBloque04 = new PdfPTable(2);
                tablaBloque04.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                tablaBloque04.setTotalWidth(550);
                float[] medidaCeldaB04 = {250, 250};
                tablaBloque04.setWidths(medidaCeldaB04);
                tablaBloque04.setHorizontalAlignment(Element.ALIGN_LEFT);
                // label RESULTADOS_DEL_TEST 
                PdfPCell lblResultadosDelTest = new PdfPCell(new Phrase("Resultados del Test: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblResultadosDelTest.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblResultadosDelTest.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblResultadosDelTest.setPaddingTop(valuePaddingTop);
                lblResultadosDelTest.setPaddingRight(valuePaddingRight);
                lblResultadosDelTest.setBorder(Rectangle.NO_BORDER);
                tablaBloque04.addCell(lblResultadosDelTest);
                // value RESULTADOS_DEL_TEST 
                PdfPCell valueResultadosDelTest = new PdfPCell(new Phrase(TXT_RESULTADOS_TEST, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueResultadosDelTest.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueResultadosDelTest.setPaddingTop(valuePaddingTop);
                valueResultadosDelTest.setBorder(Rectangle.NO_BORDER);
                tablaBloque04.addCell(valueResultadosDelTest);
                // label SUPLEMENTACION_RECETADA 
                PdfPCell lblSuplementacionRecetada = new PdfPCell(new Phrase("Suplementación Recetada: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblSuplementacionRecetada.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblSuplementacionRecetada.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblSuplementacionRecetada.setPaddingTop(valuePaddingTop);
                lblSuplementacionRecetada.setPaddingRight(valuePaddingRight);
                lblSuplementacionRecetada.setBorder(Rectangle.NO_BORDER);
                tablaBloque04.addCell(lblSuplementacionRecetada);
                // value SUPLEMENTACION_RECETADA 
                PdfPCell valueSuplementacionRecetada = new PdfPCell(new Phrase(TXT_SUPLEMENTACION_RECETADA, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueSuplementacionRecetada.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueSuplementacionRecetada.setPaddingTop(valuePaddingTop);
                valueSuplementacionRecetada.setBorder(Rectangle.NO_BORDER);
                tablaBloque04.addCell(valueSuplementacionRecetada);
                // label LABORATORIO 
                PdfPCell lblLaboratorio = new PdfPCell(new Phrase("Laboratorio: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblLaboratorio.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblLaboratorio.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblLaboratorio.setPaddingTop(valuePaddingTop);
                lblLaboratorio.setPaddingRight(valuePaddingRight);
                lblLaboratorio.setBorder(Rectangle.NO_BORDER);
                tablaBloque04.addCell(lblLaboratorio);
                // value LABORATORIO 
                PdfPCell valueLaboratorio = new PdfPCell(new Phrase(TXT_LABORATORIO, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueLaboratorio.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueLaboratorio.setPaddingTop(valuePaddingTop);
                valueLaboratorio.setBorder(Rectangle.NO_BORDER);
                tablaBloque04.addCell(valueLaboratorio);
                // label COMENTARIOS_GENERALES 
                PdfPCell lblComentarioGenerales = new PdfPCell(new Phrase("Comentarios Generales: ", FontFactory.getFont("arial", fontSize10, Font.BOLD)));
                lblComentarioGenerales.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblComentarioGenerales.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblComentarioGenerales.setPaddingTop(valuePaddingTop);
                lblComentarioGenerales.setPaddingRight(valuePaddingRight);
                lblComentarioGenerales.setBorder(Rectangle.NO_BORDER);
                tablaBloque04.addCell(lblComentarioGenerales);
                // value COMENTARIO_GENERALES 
                PdfPCell valueComentariosGenerales = new PdfPCell(new Phrase(TXT_COMENTARIOS_GENERALES, FontFactory.getFont("arial", fontSize10, Font.NORMAL)));
                valueComentariosGenerales.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueComentariosGenerales.setPaddingTop(valuePaddingTop);
                valueComentariosGenerales.setBorder(Rectangle.NO_BORDER);
                tablaBloque04.addCell(valueComentariosGenerales);
                // CARGO LA TABLA AL DOCUMENTO.-
                tablaBloque04.setSpacingBefore(15);
                documento.add(tablaBloque04);
                
                
                
//                // TABLA CON EL DETALLE DE LA FACTURA -------------------------- 
//                PdfPTable tablaDet = new PdfPTable(7);
//                tablaDet.setTotalWidth(750);
//                float[] medidaCeldaDet = {70, 180, 100, 70, 70, 120, 140};
//                tablaDet.setWidths(medidaCeldaDet);
//                tablaDet.setHorizontalAlignment(Element.ALIGN_CENTER);
//                // VARIABLES PARA ESTANDARIZAR VALORES 
//                float value5f = 5f;
//                float value10f = 10f;
//                float value20f = 20f;
//                float valPadding = 5f;
//                float valPaddingTop = 5f;
//                float valPaddingBottom = 5f;
//                // COLUMNA NRO_ITEM 
//                PdfPCell colNroItem = new PdfPCell(new Phrase("Item", FontFactory.getFont("arial", 12, Font.BOLD)));
//                colNroItem.setVerticalAlignment(1);
//                colNroItem.setHorizontalAlignment(1);
//                colNroItem.setPadding(valPadding);
//                colNroItem.setPaddingTop(valPaddingTop);
//                colNroItem.setPaddingBottom(valPaddingBottom);
//                tablaDet.addCell(colNroItem);
//                // COLUMNA DESCRIPCION 
//                PdfPCell colDescripcion = new PdfPCell(new Phrase("Descripción", FontFactory.getFont("arial", 12, Font.BOLD)));
//                colDescripcion.setVerticalAlignment(1);
//                colDescripcion.setHorizontalAlignment(1);
//                colDescripcion.setPadding(valPadding);
//                colDescripcion.setPaddingTop(valPaddingTop);
//                colDescripcion.setPaddingBottom(valPaddingBottom);
//                tablaDet.addCell(colDescripcion);
//                // COLUMNA PRECIO 
//                PdfPCell colPrecio = new PdfPCell(new Phrase("Precio", FontFactory.getFont("arial", 12, Font.BOLD)));
//                colPrecio.setVerticalAlignment(1);
//                colPrecio.setHorizontalAlignment(1);
//                colPrecio.setPadding(valPadding);
//                colPrecio.setPaddingTop(valPaddingTop);
//                colPrecio.setPaddingBottom(valPaddingBottom);
//                tablaDet.addCell(colPrecio);
//                // COLUMNA CANTIDAD 
//                PdfPCell colCantidad = new PdfPCell(new Phrase("Cant.", FontFactory.getFont("arial", 12, Font.BOLD)));
//                colCantidad.setVerticalAlignment(1);
//                colCantidad.setHorizontalAlignment(1);
//                colCantidad.setPadding(valPadding);
//                colCantidad.setPaddingTop(valPaddingTop);
//                colCantidad.setPaddingBottom(valPaddingBottom);
//                tablaDet.addCell(colCantidad);
//                // COLUMNA IVA 
//                PdfPCell colIva = new PdfPCell(new Phrase("IVA", FontFactory.getFont("arial", 12, Font.BOLD)));
//                colIva.setVerticalAlignment(1);
//                colIva.setHorizontalAlignment(1);
//                colIva.setPadding(valPadding);
//                colIva.setPaddingTop(valPaddingTop);
//                colIva.setPaddingBottom(valPaddingBottom);
//                tablaDet.addCell(colIva);
//                // COLUMNA MONTO_IVA 
//                PdfPCell colMontoIva = new PdfPCell(new Phrase("Monto IVA", FontFactory.getFont("arial", 12, Font.BOLD)));
//                colMontoIva.setVerticalAlignment(1);
//                colMontoIva.setHorizontalAlignment(1);
//                colMontoIva.setPadding(valPadding);
//                colMontoIva.setPaddingTop(valPaddingTop);
//                colMontoIva.setPaddingBottom(valPaddingBottom);
//                tablaDet.addCell(colMontoIva);
//                // COLUMNA SUBTOTAL 
//                PdfPCell colSubtotal = new PdfPCell(new Phrase("SubTotal", FontFactory.getFont("arial", 12, Font.BOLD)));
//                colSubtotal.setVerticalAlignment(1);
//                colSubtotal.setHorizontalAlignment(1);
//                colSubtotal.setPadding(valPadding);
//                colSubtotal.setPaddingTop(valPaddingTop);
//                colSubtotal.setPaddingBottom(valPaddingBottom);
//                tablaDet.addCell(colSubtotal);
//                
//                for (BeanFacturaCab detalleDatos : listaDetalle) {
//                    String NRO_ITEM = String.valueOf(detalleDatos.getOFD_ITEM());
//                    String DESCRIPCION = detalleDatos.getOFD_DESCRIPCION_AUX();
//                    String PRECIO = detalleDatos.getOFD_PRECIO();
//                    String CANTIDAD = String.valueOf(detalleDatos.getOFD_CANTIDAD());
//                    String IVA = detalleDatos.getOFD_IDIMPUESTO();
//                    String MONTO_IVA="0";
//                    if (IVA.equalsIgnoreCase("10") || IVA.equalsIgnoreCase("10%")) {
//                        IVA = IVA+"%";
//                        MONTO_IVA = detalleDatos.getOFD_IVA10();
//                    } else if(IVA.equalsIgnoreCase("5") || IVA.equalsIgnoreCase("5%")) {
//                        IVA = IVA+"%";
//                        MONTO_IVA = detalleDatos.getOFD_IVA5();
//                    }
//                    String SUBTOTAL = detalleDatos.getOFD_SUBTOTAL();
//                    
//                    System.out.println("--------DATOS_DET--------------");
//                    System.out.println("-   _NRO_ITEM:   :"+NRO_ITEM);
//                    System.out.println("-   _DESCRIP.:   :"+DESCRIPCION);
//                    System.out.println("-   ___PRECIO:   :"+PRECIO);
//                    System.out.println("-   _CANTIDAD:   :"+CANTIDAD);
//                    System.out.println("-   _______IVA:   :"+IVA);
//                    System.out.println("-   _MONTO_IVA:   :"+MONTO_IVA);
//                    System.out.println("-   __SUBTOTAL:   :"+SUBTOTAL);
//                    System.out.println("-------------------------------");
//                    
//                    // NRO_ITEM 
//                    PdfPCell cellNroItem = new PdfPCell(new Phrase(NRO_ITEM, FontFactory.getFont("arial", 11, Font.NORMAL)));
//                    cellNroItem.setUseAscender(true);
//                    cellNroItem.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                    cellNroItem.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    cellNroItem.setPadding(value5f);
//                    cellNroItem.setBorder(Rectangle.BOTTOM);
//                    cellNroItem.setBorderColor(BaseColor.GRAY);
//                    tablaDet.addCell(cellNroItem);
//                    // DESCRIPCION 
//                    PdfPCell cellDescripcion = new PdfPCell(new Phrase(DESCRIPCION, FontFactory.getFont("arial", 10, Font.NORMAL)));
//                    cellDescripcion.setUseAscender(true);
//                    cellDescripcion.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                    cellDescripcion.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    cellDescripcion.setPadding(value5f);
//                    cellDescripcion.setBorder(Rectangle.BOTTOM);
//                    cellDescripcion.setBorderColor(BaseColor.GRAY);
//                    tablaDet.addCell(cellDescripcion);
//                    // PRECIO  
//                    PdfPCell cellPrecio = new PdfPCell(new Phrase(PRECIO, FontFactory.getFont("arial", 10, Font.NORMAL)));
//                    cellPrecio.setUseAscender(true);
//                    cellPrecio.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                    cellPrecio.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellPrecio.setPadding(value5f);
//                    cellPrecio.setBorder(Rectangle.BOTTOM);
//                    cellPrecio.setBorderColor(BaseColor.GRAY);
//                    tablaDet.addCell(cellPrecio);
//                    // CANTIDAD 
//                    PdfPCell cellCantidad = new PdfPCell(new Phrase(CANTIDAD, FontFactory.getFont("arial", 11, Font.NORMAL)));
//                    cellCantidad.setUseAscender(true);
//                    cellCantidad.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                    cellCantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    cellCantidad.setPadding(value5f);
//                    cellCantidad.setBorder(Rectangle.BOTTOM);
//                    cellCantidad.setBorderColor(BaseColor.GRAY);
//                    tablaDet.addCell(cellCantidad);
//                    // IVA 
//                    PdfPCell cellIva = new PdfPCell(new Phrase(IVA, FontFactory.getFont("arial", 11, Font.NORMAL)));
//                    cellIva.setUseAscender(true);
//                    cellIva.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                    cellIva.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    cellIva.setPadding(value5f);
//                    cellIva.setBorder(Rectangle.BOTTOM);
//                    cellIva.setBorderColor(BaseColor.GRAY);
//                    tablaDet.addCell(cellIva);
//                    // MONTO_IVA 
//                    PdfPCell cellMontoIva = new PdfPCell(new Phrase(MONTO_IVA, FontFactory.getFont("arial", 10, Font.NORMAL)));
//                    cellMontoIva.setUseAscender(true);
//                    cellMontoIva.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                    cellMontoIva.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellMontoIva.setPadding(value5f);
//                    cellMontoIva.setBorder(Rectangle.BOTTOM);
//                    cellMontoIva.setBorderColor(BaseColor.GRAY);
//                    tablaDet.addCell(cellMontoIva);
//                    // SUBTOTAL 
//                    PdfPCell cellSubtotal = new PdfPCell(new Phrase(SUBTOTAL, FontFactory.getFont("arial", 10, Font.NORMAL)));
//                    cellSubtotal.setUseAscender(true);
//                    cellSubtotal.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                    cellSubtotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellSubtotal.setPadding(value5f);
//                    cellSubtotal.setBorder(Rectangle.BOTTOM);
//                    cellSubtotal.setBorderColor(BaseColor.GRAY);
//                    tablaDet.addCell(cellSubtotal);
//                } // END FOR 
//                tablaDet.setSpacingBefore(15);
//                documento.add(tablaDet);
                
                



//                // TABLA PARA EL PIE DE PAGINA CON LOS TOTALES DE LA FACTURA -------------------------- 
//                PdfPTable tablaTotales = new PdfPTable(2);
////                tablaTotales.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//                tablaTotales.setTotalWidth(200);
//                float[] medidaCeldasTotales = {60, 140};
//                tablaTotales.setWidths(medidaCeldasTotales);
//                tablaTotales.setHorizontalAlignment(Element.ALIGN_LEFT);
//                tablaTotales.setWidthPercentage(100);
//                // DATOS DE LA TABLA 
//                // LABEL, LIQUIDACION IVA 
//                PdfPCell lblLiqIva = new PdfPCell(new Phrase("Liquidación IVA ", FontFactory.getFont("arial", 12, Font.BOLD)));
//                lblLiqIva.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                lblLiqIva.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                lblLiqIva.setPaddingTop(value10f);
//                lblLiqIva.setPaddingRight(value20f);
//                lblLiqIva.setBorder(Rectangle.NO_BORDER);
//                tablaTotales.addCell(lblLiqIva);
//                // VALUE, LIQUIDACION IVA / OBS.: LO DEJO VACIO YA QUE LIQUIDACION IVA QUIERO UTILIZARLO COMO UN SUBTITULO Y POR ESO LE CARGO UN VALOR VACIO PARA QUE ASI EL SIGUIENTE LABEL NO SE COLOQUE ALADO DE ESTE SINO ABAJO  
//                PdfPCell lblLiqIvaVacio = new PdfPCell(new Phrase(" ", FontFactory.getFont("arial", 12, Font.BOLD)));
//                lblLiqIvaVacio.setBorder(Rectangle.NO_BORDER);
//                tablaTotales.addCell(lblLiqIvaVacio);
//                // LABEL, TOTAL_IVA_5 
//                PdfPCell lblIva5 = new PdfPCell(new Phrase("(5%): ", FontFactory.getFont("arial", 11, Font.BOLD)));
//                lblIva5.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                lblIva5.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                lblIva5.setPaddingTop(value10f);
//                lblIva5.setPaddingRight(value20f);
//                lblIva5.setBorder(Rectangle.NO_BORDER);
//                tablaTotales.addCell(lblIva5);
//                // VALUE, TOTAL_IVA_5 
//                PdfPCell valueIva5 = new PdfPCell(new Phrase(TXT_TOTAL_IVA5, FontFactory.getFont("arial", 12, Font.NORMAL)));
//                valueIva5.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                valueIva5.setHorizontalAlignment(Element.ALIGN_LEFT);
//                valueIva5.setPaddingTop(value10f);
//                valueIva5.setBorder(Rectangle.NO_BORDER);
//                tablaTotales.addCell(valueIva5);
//                // LABEL, TOTAL_IVA_10 
//                PdfPCell lblIva10 = new PdfPCell(new Phrase("(10%): ", FontFactory.getFont("arial", 11, Font.BOLD)));
//                lblIva10.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                lblIva10.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                lblIva10.setPaddingTop(value10f);
//                lblIva10.setPaddingRight(value20f);
//                lblIva10.setBorder(Rectangle.NO_BORDER);
//                tablaTotales.addCell(lblIva10);
//                // VALUE, TOTAL_IVA_10 
//                PdfPCell valueIva10 = new PdfPCell(new Phrase(TXT_TOTAL_IVA10, FontFactory.getFont("arial", 12, Font.NORMAL)));
//                valueIva10.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                valueIva10.setHorizontalAlignment(Element.ALIGN_LEFT);
//                valueIva10.setPaddingTop(value10f);
//                valueIva10.setBorder(Rectangle.NO_BORDER);
//                tablaTotales.addCell(valueIva10);
//                // LABEL, TOTAL_IVA 
//                PdfPCell lblIva = new PdfPCell(new Phrase("Total IVA: ", FontFactory.getFont("arial", 11, Font.BOLD)));
//                lblIva.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                lblIva.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                lblIva.setPaddingTop(value10f);
//                lblIva.setPaddingRight(value20f);
//                lblIva.setBorder(Rectangle.NO_BORDER);
//                tablaTotales.addCell(lblIva);
//                // VALUE, TOTAL_IVA 
//                PdfPCell valueIva = new PdfPCell(new Phrase(TXT_TOTAL_IVA, FontFactory.getFont("arial", 12, Font.NORMAL)));
//                valueIva.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                valueIva.setHorizontalAlignment(Element.ALIGN_LEFT);
//                valueIva.setPaddingTop(value10f);
//                valueIva.setBorder(Rectangle.NO_BORDER);
//                tablaTotales.addCell(valueIva);
//                // LABEL, TOTAL_FACTURA 
//                PdfPCell lblTotalIva = new PdfPCell(new Phrase("Total: ", FontFactory.getFont("arial", 11, Font.BOLD)));
//                lblTotalIva.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                lblTotalIva.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                lblTotalIva.setPaddingTop(value10f);
//                lblTotalIva.setPaddingRight(value20f);
//                lblTotalIva.setBorder(Rectangle.NO_BORDER);
//                tablaTotales.addCell(lblTotalIva);
//                // VALUE, TOTAL_FACTURA 
//                PdfPCell valueTotalIva = new PdfPCell(new Phrase(TXT_TOTAL, FontFactory.getFont("arial", 12, Font.NORMAL)));
//                valueTotalIva.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                valueTotalIva.setHorizontalAlignment(Element.ALIGN_LEFT);
//                valueTotalIva.setPaddingTop(value10f);
//                valueTotalIva.setBorder(Rectangle.NO_BORDER);
//                tablaTotales.addCell(valueTotalIva);
//                
//                tablaTotales.setSpacingBefore(15);
//                documento.add(tablaTotales);
                
                documento.add(Chunk.NEWLINE);
                documento.close();
            // -----------------------------------------------------------------------------------------------------
            // -----------------------------------------------------------------------------------------------------
                
                // Hay que configurar las cabeceras para que el navegador detecte que es un PDF 
                response.setHeader("Expires", "0");
                response.setHeader("Cache - Control", "must - revalidate, post - check = 0, pre - check = 0");
                response.setHeader("Pragma", "public  ");
                // Configuramos el content type 
                response.setContentType("application/pdf");
                // Tamaño 
                response.setContentLength(baos.size());
                // Escribir el ByteArrayOutputStream a el ServletOutputStream 
                OutputStream os = response.getOutputStream();
                baos.writeTo(os);
                os.flush();
                os.close();
        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
    } // END DO_POST 
    
} // END CLASS 