/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.modelo.pdf;

import com.itextpdf.text.BaseColor;
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
import java.util.List;
import com.agend.javaBean.BeanFacturaCab;
import com.agend.javaBean.BeanPersona;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.agend.modelo.ModelFactura;
import com.agend.modelo.ModelInicioSesion;
import com.agend.modelo.ModelPersona;

/**
 *
 * @author RYUU
 */
@WebServlet(name="crearPdfFacturaListaResumen", urlPatterns="/lista_resumen_factura_pdf")
public class CrearPdfFacturaListaResumen extends HttpServlet {
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
        doPost(request, response);
    }
    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Document documento = new Document(PageSize.LEGAL);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(documento, baos);
            
            documento.open();
            
            HttpSession sesionDatosUsuario = request.getSession();
            DecimalFormat formatear = new DecimalFormat("###,###,###");
            ModelInicioSesion metodosIniSes = new ModelInicioSesion();
            ModelPersona metodosPersona = new ModelPersona();
            ModelFactura metodosFactura = new ModelFactura();
            List<BeanFacturaCab> listaFactura;
            if (((List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("PDF_LISTA_RPT_RESUMEN_FACTURA")) != null) {
                listaFactura = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("PDF_LISTA_RPT_RESUMEN_FACTURA");
            } else {
                String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
                System.out.println(".   _PDF__ID_USUARIO_PERSONA:    :"+SESION_IDUSER);
                com.agend.modelo.ModelPerfil modeloPerfil = new com.agend.modelo.ModelPerfil();
                String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
                System.out.println(".   _PDF__ID_PERFIL_USER:        :"+IDPERFIL_USER);
                
                // NRO PAG ACTUAL A MOSTRAR 
                String NRO_PAG;
                if (String.valueOf(request.getParameter("PDFNPA")).isEmpty() || request.getParameter("PDFNPA") == null) {
//                if (String.valueOf(sesionDatosUsuario.getAttribute("PDF_FACT_LISTARESU_VAR_AUX_NRO_PAG")).isEmpty() || sesionDatosUsuario.getAttribute("PDF_FACT_LISTARESU_VAR_AUX_NRO_PAG") == null) {
                    NRO_PAG = "1";
                } else {
                    NRO_PAG = (String) request.getParameter("PDFNPA");
//                    NRO_PAG = (String) sesionDatosUsuario.getAttribute("PDF_FACT_LISTARESU_VAR_AUX_NRO_PAG");
                }
                System.out.println("_    ___PDF____NRO_PAG_ACTUAL:     :"+NRO_PAG);
                // NRO DE REGISTRO A MOSTRAR 
                String NRO_REG_MOSTRAR;
                if (String.valueOf(request.getParameter("PDFNCRM")).isEmpty() || request.getParameter("PDFNCRM") == null) { // SI SE ENCUENTRA NULL ENTONCES MOSTRARIA EL MENOR NUMERO DE REGISTROS ESTABLECIDOS EN EL METODO DE INICIO DE SESION 
//                if (String.valueOf(sesionDatosUsuario.getAttribute("PDF_FACT_LISTARESU_VAR_AUX_NRO_REG")).isEmpty() || sesionDatosUsuario.getAttribute("PDF_FACT_LISTARESU_VAR_AUX_NRO_REG") == null) { // SI SE ENCUENTRA NULL ENTONCES MOSTRARIA EL MENOR NUMERO DE REGISTROS ESTABLECIDOS EN EL METODO DE INICIO DE SESION 
                    NRO_REG_MOSTRAR = metodosIniSes.minNroCbxCantFil();
                } else {
                    NRO_REG_MOSTRAR = (String) request.getParameter("PDFNCRM");
//                    NRO_REG_MOSTRAR = (String) sesionDatosUsuario.getAttribute("PDF_FACT_LISTARESU_VAR_AUX_NRO_REG");
                }
                System.out.println("_    ___PDF____NRO_REG_MOSTRAR:     :"+NRO_REG_MOSTRAR);
                
//                // OBTENGO EL NUMERO DE PAGINA ACTUAL QUE LA GRILLA VA A MOSTRAR 
//                String NRO_PAG = "";
//                if (sesionDatosUsuario.getAttribute("PAG_RPT_FACT_LISTA_ACTUAL") == null) {
//                    NRO_PAG = "1";
//                } else {
//                    NRO_PAG = (String) sesionDatosUsuario.getAttribute("PAG_RPT_FACT_LISTA_ACTUAL");
//                }
//                System.out.println("_   _JSP____NRO_PAG_ACTUAL:     :"+NRO_PAG);
//                String cant_min_fija = metodosIniSes.minNroCbxCantFil(); // NRO DE REGISTROS A MOSTRAR EN LA GRILLA QUE MUESTRA EL COMBO POR DEFECTO 
                /*
                 * OBSERVACION SOBRE EL LLAMADO AL METODO: 
                    EN EL PROYECTO DE EMPEÑO AL USAR EL PDF PARA CREAR UN REPORTE PARA LA FACTURA COMO EN ESTE PROYECTO 
                    UTILIZO UNA BANDERA PARA IDENTIFICAR DE DONDE VIENE LA ACTIVACION DEL PDF, EJ. SI 1 SI FUERA DE RPT FACTURA Y 2 SI VIENE DE ANULAR FACTURA 
                    YA QUE AMBAS PAGINAS TIENEN SELECT DIFERENTE Y NO ME SIRVE LLAMAR AL MISMO METODO PARA AMBAS, ENTONCES PARA LLAMAR AL CARGA GRILLA CORRECTO LO HACIA A TRAVES DE LA BANDERA 
                    PERO EN ESTE PROYECTO NO UTILIZO EN OTRAS PAGINAS EL PDF PARA CREAR LA LISTA DE LAS FACTURAS 
                    PERO SI EN EL FUTURO UTILIZO ESTE MISMO PDF PARA MAS DE UNA PAGINA QUE NO SEA LA DE REPORTE FACTURA TENDRIA QUE CREAR UNA BANDERA EN LA SESION PARA IDENTIFICAR DE DONDE VIENE Y ASI LLAMAR AL METODO CORRECTO 
                    Y NO PASE QUE EN LA PAGINA SE ESTE VIENDO UNOS REGISTROS Y AL IMPRIMIR EL PDF SE VEA OTROS O MAS REGISTROS DE LO QUE SE VEA.-
                */
                if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) { // 4 : PACIENTE 
                    listaFactura = metodosFactura.cargar_grilla_paginacion_rtp_fact(sesionDatosUsuario, NRO_PAG, NRO_REG_MOSTRAR, SESION_IDUSER);
//                } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER)) { //  1 : ADMIN  -  3 : SECRETARIO 
                } else { // SUPONGO QUE SI NO ES PACIENTE SERA ADMIN O SECRETARIO O OTRO PERFIL, PERO A NINGUNO DE ESOS PERFILES LE QUIERO MOSTRAR UN SELECT DIFERENTE COMO A PACIENTE, ENTONCES POR ESO LE AGREGO EL ELSE NOMAS, EN CASO DE QUE QUIERA HACER UN SELECT APARTE, ENTONCES AHI DIVIDO CON UN ELSE IF 
                    listaFactura = metodosFactura.cargar_grilla_paginacion_rtp_fact(sesionDatosUsuario, NRO_PAG, NRO_REG_MOSTRAR, "");
                }
//                listaFactura = metodosFactura.cargar_grilla(sesionDatosUsuario);
            }
//            Iterator<BeanFacturaCab> iterListFact = listaFactura.iterator();
//            BeanFacturaCab datosFactura = new BeanFacturaCab();
//            String NRO_FACTURA="", IDCLIENTE="", CINRO_CLIENTE="", CLIENTE="", FECHA_FACTURA="", TOTAL="";
            BeanPersona datosPersona = new BeanPersona();
            
            // VARIABLES PARA ESTANDARIZAR LOS VALORES
            float value5f = 5f;
            float value10f = 10f;
            float value15f = 15f;
            float value20f = 20f;
            
            documento.addTitle("Reporte Resumen Factura");
            String empresa = metodosIniSes.devolverEmpresa();
            Paragraph titulo = new Paragraph(empresa, FontFactory.getFont("arial", 16, Font.BOLD));
            titulo.setAlignment(1);
            titulo.setSpacingAfter(value10f);
            documento.add(titulo);
            
            Paragraph subtitulo = new Paragraph("Resumen de Facturas", FontFactory.getFont("arial", 15, Font.BOLD));
            subtitulo.setAlignment(1);
            subtitulo.setSpacingAfter(value5f);
            documento.add(subtitulo);
            
            PdfPTable tablaDetalle = new PdfPTable(9);
            tablaDetalle.setTotalWidth(710);
            float[] medidaCeldaDet = {70, 80, 50, 100, 80, 80, 80, 80, 90};
            tablaDetalle.setWidths(medidaCeldaDet);
            tablaDetalle.setWidthPercentage(100);
            tablaDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            // COLUMNA FECHA_FACTURA 
            PdfPCell colFechaFactura = new PdfPCell(new Phrase("Fecha", FontFactory.getFont("arial", 10, Font.BOLD)));
            colFechaFactura.setVerticalAlignment(1);
            colFechaFactura.setHorizontalAlignment(1);
            colFechaFactura.setPadding(value5f);
            colFechaFactura.setPaddingTop(value10f);
            colFechaFactura.setPaddingBottom(value5f);
            tablaDetalle.addCell(colFechaFactura);
            // COLUMNA NRO_FACTURA 
            PdfPCell colNroFactura = new PdfPCell(new Phrase("Nro. Factura", FontFactory.getFont("arial", 10, Font.BOLD)));
            colNroFactura.setVerticalAlignment(1);
            colNroFactura.setHorizontalAlignment(1);
            colNroFactura.setPadding(value5f);
            colNroFactura.setPaddingTop(value5f);
            colNroFactura.setPaddingBottom(value5f);
            tablaDetalle.addCell(colNroFactura);
            // COLUMNA TIPO_FACTURA 
            PdfPCell colTipoFact = new PdfPCell(new Phrase("Tipo", FontFactory.getFont("arial", 10, Font.BOLD)));
            colTipoFact.setVerticalAlignment(1);
            colTipoFact.setHorizontalAlignment(1);
            colTipoFact.setPadding(value5f);
            colTipoFact.setPaddingTop(value10f);
            colTipoFact.setPaddingBottom(value5f);
            tablaDetalle.addCell(colTipoFact);
            // COLUMNA CLIENTE 
            PdfPCell colCliente = new PdfPCell(new Phrase("Cliente", FontFactory.getFont("arial", 10, Font.BOLD)));
            colCliente.setVerticalAlignment(1);
            colCliente.setHorizontalAlignment(Element.ALIGN_LEFT);
            colCliente.setPadding(value5f);
            colCliente.setPaddingTop(value10f);
            colCliente.setPaddingBottom(value5f);
            tablaDetalle.addCell(colCliente);
            // COLUMNA GRAVADA_10%  
            PdfPCell colGrav10 = new PdfPCell(new Phrase("Grav. 10%", FontFactory.getFont("arial", 10, Font.BOLD)));
            colGrav10.setVerticalAlignment(1);
            colGrav10.setHorizontalAlignment(1);
            colGrav10.setPadding(value5f);
            colGrav10.setPaddingTop(value10f);
            colGrav10.setPaddingBottom(value5f);
            tablaDetalle.addCell(colGrav10);
            // COLUMNA IVA_10%  
            PdfPCell colIva10 = new PdfPCell(new Phrase("IVA 10%", FontFactory.getFont("arial", 10, Font.BOLD)));
            colIva10.setVerticalAlignment(1);
            colIva10.setHorizontalAlignment(1);
            colIva10.setPadding(value5f);
            colIva10.setPaddingTop(value10f);
            colIva10.setPaddingBottom(value5f);
            tablaDetalle.addCell(colIva10);
            // COLUMNA GRAVADA_5%  
            PdfPCell colGrav5 = new PdfPCell(new Phrase("Grav. 5%", FontFactory.getFont("arial", 10, Font.BOLD)));
            colGrav5.setVerticalAlignment(1);
            colGrav5.setHorizontalAlignment(1);
            colGrav5.setPadding(value5f);
            colGrav5.setPaddingTop(value10f);
            colGrav5.setPaddingBottom(value5f);
            tablaDetalle.addCell(colGrav5);
            // COLUMNA IVA_5%  
            PdfPCell colIva5 = new PdfPCell(new Phrase("IVA 5%", FontFactory.getFont("arial", 10, Font.BOLD)));
            colIva5.setVerticalAlignment(1);
            colIva5.setHorizontalAlignment(1);
            colIva5.setPadding(value5f);
            colIva5.setPaddingTop(value10f);
            colIva5.setPaddingBottom(value5f);
            tablaDetalle.addCell(colIva5);
            // COLUMNA TOTAL_FACTURA  
            PdfPCell colTotal = new PdfPCell(new Phrase("TOTAL", FontFactory.getFont("arial", 10, Font.BOLD)));
            colTotal.setVerticalAlignment(1);
            colTotal.setHorizontalAlignment(1);
            colTotal.setPadding(value5f);
            colTotal.setPaddingTop(value10f);
            colTotal.setPaddingBottom(value5f);
            tablaDetalle.addCell(colTotal);
            
            // VARIABLES QUE UTILIZARE PARA CALCULAR LOS TOTALES DE CADA COLUMNA PARA LUEGO UTILIZARLOS PARA EL PIE DE PAGINA 
            double VAR_TOTAL_GRAV10=0, VAR_TOTAL_IVA10=0, VAR_TOTAL_GRAV5=0, VAR_TOTAL_IVA5=0, VAR_TOTAL_IVA=0, VAR_TOTAL=0;
            
            for (BeanFacturaCab detalleDatos : listaFactura) {
                String gravada10, iva10, gravada5, iva5;
                String estado_fact = detalleDatos.getOF_ESTADO();
                String fechafactura = detalleDatos.getOF_FECHAFACTURA();
                String nrofactura = detalleDatos.getOF_NROFACTURA();
                String tipofactura = detalleDatos.getOF_TIPOFACTURA();
                String idcliente = String.valueOf(detalleDatos.getOF_IDCLIENTE());
                if (Double.parseDouble(detalleDatos.getOF_TOTAL_GRAV10()) == 0) { // VALIDACION PARA EVITAR QUE SALTE UNA EXCEPCION POR QUERER FORMATEAR UN VALOR CERO 
                    gravada10="0";
                } else {
                    gravada10 = formatear.format(Double.parseDouble(detalleDatos.getOF_TOTAL_GRAV10()));
                    if (estado_fact.equalsIgnoreCase("X") == false) { // si la factura no se encuentra anulada, entonces sumaria al total del gravada 10 
                        VAR_TOTAL_GRAV10 = VAR_TOTAL_GRAV10 + Double.parseDouble(detalleDatos.getOF_TOTAL_GRAV10());
                    }
                }
                if (Double.parseDouble(detalleDatos.getOF_TOTAL_IVA10()) == 0) { // VALIDACION PARA EVITAR QUE SALTE UNA EXCEPCION POR QUERER FORMATEAR UN VALOR CERO 
                    iva10="0";
                } else {
                    iva10 = formatear.format(Double.parseDouble(detalleDatos.getOF_TOTAL_IVA10()));
                    if (estado_fact.equalsIgnoreCase("X") == false) { // si la factura no se encuentra anulada, entonces sumaria al total del iva 10 
                        VAR_TOTAL_IVA10 = VAR_TOTAL_IVA10 + Double.parseDouble(detalleDatos.getOF_TOTAL_IVA10());
                    }
                }
                if (Double.parseDouble(detalleDatos.getOF_TOTAL_GRAV5()) == 0) { // VALIDACION PARA EVITAR QUE SALTE UNA EXCEPCION POR QUERER FORMATEAR UN VALOR CERO 
                    gravada5="0";
                } else {
                    gravada5 = formatear.format(Double.parseDouble(detalleDatos.getOF_TOTAL_GRAV5()));
                    if (estado_fact.equalsIgnoreCase("X") == false) { // si la factura no se encuentra anulada, entonces sumaria al total del gravada 5 
                        VAR_TOTAL_GRAV5 = VAR_TOTAL_GRAV5 + Double.parseDouble(detalleDatos.getOF_TOTAL_GRAV5());
                    }
                }
                if (Double.parseDouble(detalleDatos.getOF_TOTAL_IVA5()) == 0) { // VALIDACION PARA EVITAR QUE SALTE UNA EXCEPCION POR QUERER FORMATEAR UN VALOR CERO 
                    iva5="0";
                } else {
                    iva5 = formatear.format(Double.parseDouble(detalleDatos.getOF_TOTAL_IVA5()));
                    if (estado_fact.equalsIgnoreCase("X") == false) { // si la factura no se encuentra anulada, entonces sumaria al total del iva 5 
                        VAR_TOTAL_IVA5 = VAR_TOTAL_IVA5 + Double.parseDouble(detalleDatos.getOF_TOTAL_IVA5());
                    }
                }
                if (estado_fact.equalsIgnoreCase("X") == false) {// si el estado de la factura no fuese anulado entonces ahi si sumaria para obtener el total del iva 
                    VAR_TOTAL_IVA = VAR_TOTAL_IVA + Double.parseDouble(detalleDatos.getOF_TOTAL_IVA10()) + Double.parseDouble(detalleDatos.getOF_TOTAL_IVA5());
                }
                String totalfactura = formatear.format(Double.parseDouble(detalleDatos.getOF_TOTAL_FACTURA()));
                if (estado_fact.equalsIgnoreCase("X") == false) {// si el estado de la factura no fuese anulado entonces ahi si sumaria para obtener el total 
                    VAR_TOTAL = VAR_TOTAL + Double.parseDouble(detalleDatos.getOF_TOTAL_FACTURA());
                }
//                VAR_TOTAL = VAR_TOTAL + Double.parseDouble(detalleDatos.getOF_TOTAL_FACTURA());
                
                // RECUPERO LOS DATOS DEL CLIENTE 
                datosPersona = metodosPersona.datosBasicosPersona(datosPersona, idcliente);
                String cinro = datosPersona.getRP_CINRO();
                String cliente = datosPersona.getRP_NOMBRE()+" "+datosPersona.getRP_APELLIDO();
                
                System.out.println("------------  DATOS  -------------");
                System.out.println("__  ESTADO_FACT:      :"+estado_fact);
                System.out.println("__  NRO_FACTURA:      :"+nrofactura);
                System.out.println("__   ID_CLIENTE:      :"+idcliente);
                System.out.println("__      CLIENTE:      :"+cliente);
                System.out.println("__       CI_NRO:      :"+cinro);
                System.out.println("__FECHA_FACTURA:      :"+fechafactura);
                System.out.println("__TOTAL_GRAV_10:      :"+gravada10);
                System.out.println("__TOTAL_IVA_10%:      :"+iva10);
                System.out.println("___TOTAL_GRAV_5:      :"+gravada5);
                System.out.println("___TOTAL_IVA_5%:      :"+iva5);
                System.out.println("__TOTAL_FACTURA:      :"+totalfactura);
                System.out.println("------------ ------- -------------");
                
                // VALIDO PARA CAMBIAR EL TOTAL DE LA FACTURA POR CERO PARA PODER EVITAR QUE SE SUME LOS TOTALES DE LAS FACTURAS ANULADAS 
                if (estado_fact.equalsIgnoreCase("X")) {
                    totalfactura = "0";
                }
                // FECHA_FACTURA 
                PdfPCell cellFechaFact = new PdfPCell(new Phrase(fechafactura, FontFactory.getFont("arial", 8, Font.NORMAL)));
                cellFechaFact.setUseAscender(true);
                cellFechaFact.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellFechaFact.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellFechaFact.setPadding(value5f);
                cellFechaFact.setBorder(Rectangle.BOTTOM);
                cellFechaFact.setBorderColor(BaseColor.GRAY);
                tablaDetalle.addCell(cellFechaFact);
                // NRO_FACTURA 
                PdfPCell cellNroFactura = new PdfPCell(new Phrase(nrofactura, FontFactory.getFont("arial", 8, Font.NORMAL)));
                cellNroFactura.setUseAscender(true);
                cellNroFactura.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellNroFactura.setHorizontalAlignment(Element.ALIGN_LEFT);
                cellNroFactura.setPadding(value5f);
                cellNroFactura.setPaddingLeft(value10f);
                cellNroFactura.setBorder(Rectangle.BOTTOM);
                cellNroFactura.setBorderColor(BaseColor.GRAY);
                tablaDetalle.addCell(cellNroFactura);
                // TIPO_FACTURA 
                PdfPCell cellTipoFact = new PdfPCell(new Phrase(tipofactura, FontFactory.getFont("arial", 8, Font.NORMAL)));
                cellTipoFact.setUseAscender(true);
                cellTipoFact.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellTipoFact.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellTipoFact.setPadding(value5f);
                cellTipoFact.setPaddingLeft(value10f);
                cellTipoFact.setBorder(Rectangle.BOTTOM);
                cellTipoFact.setBorderColor(BaseColor.GRAY);
                tablaDetalle.addCell(cellTipoFact);
                // CLIENTE 
                PdfPCell cellCliente = new PdfPCell(new Phrase(cliente, FontFactory.getFont("arial", 8, Font.NORMAL)));
                cellCliente.setUseAscender(true);
                cellCliente.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellCliente.setHorizontalAlignment(Element.ALIGN_LEFT);
                cellCliente.setPadding(value5f);
                cellCliente.setBorder(Rectangle.BOTTOM);
                cellCliente.setBorderColor(BaseColor.GRAY);
                tablaDetalle.addCell(cellCliente);
                // GRAVADA_10% 
                PdfPCell cellGrav10 = new PdfPCell(new Phrase(gravada10, FontFactory.getFont("arial", 8, Font.NORMAL)));
                cellGrav10.setUseAscender(true);
                cellGrav10.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellGrav10.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellGrav10.setPadding(value5f);
                cellGrav10.setBorder(Rectangle.BOTTOM);
                cellGrav10.setBorderColor(BaseColor.GRAY);
                tablaDetalle.addCell(cellGrav10);
                // IVA_10% 
                PdfPCell cellIva10 = new PdfPCell(new Phrase(iva10, FontFactory.getFont("arial", 8, Font.NORMAL)));
                cellIva10.setUseAscender(true);
                cellIva10.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellIva10.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellIva10.setPadding(value5f);
                cellIva10.setBorder(Rectangle.BOTTOM);
                cellIva10.setBorderColor(BaseColor.GRAY);
                tablaDetalle.addCell(cellIva10);
                // GRAVADA_5% 
                PdfPCell cellGrav5 = new PdfPCell(new Phrase(gravada5, FontFactory.getFont("arial", 8, Font.NORMAL)));
                cellGrav5.setUseAscender(true);
                cellGrav5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellGrav5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellGrav5.setPadding(value5f);
                cellGrav5.setBorder(Rectangle.BOTTOM);
                cellGrav5.setBorderColor(BaseColor.GRAY);
                tablaDetalle.addCell(cellGrav5);
                // IVA_5% 
                PdfPCell cellIva5 = new PdfPCell(new Phrase(iva5, FontFactory.getFont("arial", 8, Font.NORMAL)));
                cellIva5.setUseAscender(true);
                cellIva5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellIva5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellIva5.setPadding(value5f);
                cellIva5.setBorder(Rectangle.BOTTOM);
                cellIva5.setBorderColor(BaseColor.GRAY);
                tablaDetalle.addCell(cellIva5);
                // TOTAL_FACTURA 
                PdfPCell cellTotalFact = new PdfPCell(new Phrase(totalfactura, FontFactory.getFont("arial", 8, Font.NORMAL)));
                cellTotalFact.setUseAscender(true);
                cellTotalFact.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellTotalFact.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellTotalFact.setPadding(value5f);
                cellTotalFact.setPaddingRight(value10f);
                cellTotalFact.setBorder(Rectangle.BOTTOM);
                cellTotalFact.setBorderColor(BaseColor.GRAY);
                tablaDetalle.addCell(cellTotalFact);
            } // end for 
            tablaDetalle.setSpacingBefore(value15f);
            documento.add(tablaDetalle);
//            documento.add(Chunk.NEWLINE);
            
//            // CREAMOS OTRA TABLA PARA AGREGAR LOS TOTALES Y SOLAMENTE LE MOSTRALMOS EL BORDE DE ARRIBA PARA QUE PAREZCA UNA LINEA SEPARATORIA 
//            PdfPTable tablaTotales = new PdfPTable(6);
//            tablaTotales.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//            tablaTotales.setTotalWidth(520);
//            float[] medidaCellTotales = {100, 80, 80, 80, 80, 80};
//            tablaTotales.setWidths(medidaCellTotales);
//            tablaTotales.setWidthPercentage(100);
//            tablaTotales.setHorizontalAlignment(Element.ALIGN_CENTER);
//            // COLUMNA LABEL_TOTALES 
//            PdfPCell colLabelTotales = new PdfPCell(new Phrase("Totales:", FontFactory.getFont("arial", 10, Font.BOLD)));
//            colLabelTotales.setBorder(Rectangle.TOP);
//            colLabelTotales.setVerticalAlignment(1);
//            colLabelTotales.setHorizontalAlignment(1);
//            colLabelTotales.setPadding(value5f);
//            colLabelTotales.setPaddingTop(value5f);
//            colLabelTotales.setPaddingBottom(value5f);
//            tablaTotales.addCell(colLabelTotales);
//            // COLUMNA TOTAL_GRAVADA_10% 
//            PdfPCell colTotGrav10 = new PdfPCell(new Phrase(formatear.format(VAR_TOTAL_GRAV10), FontFactory.getFont("arial", 9, Font.NORMAL)));
//            colTotGrav10.setBorder(Rectangle.TOP);
//            colTotGrav10.setVerticalAlignment(1);
//            colTotGrav10.setHorizontalAlignment(1);
//            colTotGrav10.setPadding(value5f);
//            colTotGrav10.setPaddingTop(value5f);
//            colTotGrav10.setPaddingBottom(value5f);
//            tablaTotales.addCell(colTotGrav10);
//            // COLUMNA TOTAL_IVA_10% 
//            PdfPCell colTotIva10 = new PdfPCell(new Phrase(formatear.format(VAR_TOTAL_IVA10), FontFactory.getFont("arial", 9, Font.NORMAL)));
//            colTotIva10.setBorder(Rectangle.TOP);
//            colTotIva10.setVerticalAlignment(1);
//            colTotIva10.setHorizontalAlignment(1);
//            colTotIva10.setPadding(value5f);
//            colTotIva10.setPaddingTop(value5f);
//            colTotIva10.setPaddingBottom(value5f);
//            tablaTotales.addCell(colTotIva10);
//            // COLUMNA TOTAL_GRAVADA_5% 
//            PdfPCell colTotGrav5 = new PdfPCell(new Phrase(formatear.format(VAR_TOTAL_GRAV5), FontFactory.getFont("arial", 9, Font.NORMAL)));
//            colTotGrav5.setBorder(Rectangle.TOP);
//            colTotGrav5.setVerticalAlignment(1);
//            colTotGrav5.setHorizontalAlignment(1);
//            colTotGrav5.setPadding(value5f);
//            colTotGrav5.setPaddingTop(value5f);
//            colTotGrav5.setPaddingBottom(value5f);
//            tablaTotales.addCell(colTotGrav5);
//            // COLUMNA TOTAL_IVA_10% 
//            PdfPCell colTotIva5 = new PdfPCell(new Phrase(formatear.format(VAR_TOTAL_IVA5), FontFactory.getFont("arial", 9, Font.NORMAL)));
//            colTotIva5.setBorder(Rectangle.TOP);
//            colTotIva5.setVerticalAlignment(1);
//            colTotIva5.setHorizontalAlignment(1);
//            colTotIva5.setPadding(value5f);
//            colTotIva5.setPaddingTop(value5f);
//            colTotIva5.setPaddingBottom(value5f);
//            tablaTotales.addCell(colTotIva5);
//            // COLUMNA TOTAL_FACTURAS 
//            PdfPCell colTotalFacturas = new PdfPCell(new Phrase(formatear.format(VAR_TOTAL), FontFactory.getFont("arial", 9, Font.NORMAL)));
//            colTotalFacturas.setBorder(Rectangle.TOP);
//            colTotalFacturas.setVerticalAlignment(1);
//            colTotalFacturas.setHorizontalAlignment(1);
//            colTotalFacturas.setPadding(value5f);
//            colTotalFacturas.setPaddingTop(value5f);
//            colTotalFacturas.setPaddingBottom(value5f);
//            tablaTotales.addCell(colTotalFacturas);
//            documento.add(tablaTotales);
//            documento.add(Chunk.NEWLINE);
            
            
            
                // TABLA PARA EL PIE DE PAGINA CON LOS TOTALES DE LA FACTURA -------------------------- 
                PdfPTable tablaTotales = new PdfPTable(2);
                tablaTotales.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                tablaTotales.setTotalWidth(200);
                float[] medidaCeldasTotales = {60, 140};
                tablaTotales.setWidths(medidaCeldasTotales);
                tablaTotales.setHorizontalAlignment(Element.ALIGN_LEFT);
                tablaTotales.setWidthPercentage(100);
                // DATOS DE LA TABLA 
                // LABEL, LIQUIDACION IVA 
                PdfPCell lblTotalesFactura = new PdfPCell(new Phrase("Totales: ", FontFactory.getFont("arial", 12, Font.BOLD)));
//                lblTotalesFactura.setBorder(Rectangle.BOTTOM);
                lblTotalesFactura.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblTotalesFactura.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblTotalesFactura.setPaddingTop(value10f);
                lblTotalesFactura.setPaddingRight(value20f);
                lblTotalesFactura.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(lblTotalesFactura);
                // VALUE, LIQUIDACION IVA / OBS.: LO DEJO VACIO YA QUE LIQUIDACION IVA QUIERO UTILIZARLO COMO UN SUBTITULO Y POR ESO LE CARGO UN VALOR VACIO PARA QUE ASI EL SIGUIENTE LABEL NO SE COLOQUE ALADO DE ESTE SINO ABAJO  
                PdfPCell lblLiqIvaVacio = new PdfPCell(new Phrase(" ", FontFactory.getFont("arial", 11, Font.BOLD)));
                lblLiqIvaVacio.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(lblLiqIvaVacio);
                // LABEL, TOTAL_GRAVADA_10% 
                PdfPCell lblGrav10 = new PdfPCell(new Phrase("Gravada 10%: ", FontFactory.getFont("arial", 10, Font.BOLD)));
                lblGrav10.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblGrav10.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblGrav10.setPaddingTop(value10f);
                lblGrav10.setPaddingRight(value20f);
                lblGrav10.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(lblGrav10);
                // VALUE, TOTAL_GRAVADA_10% 
                PdfPCell valueGrav10 = new PdfPCell(new Phrase(formatear.format(VAR_TOTAL_GRAV10), FontFactory.getFont("arial", 11, Font.NORMAL)));
                valueGrav10.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueGrav10.setHorizontalAlignment(Element.ALIGN_LEFT);
                valueGrav10.setPaddingTop(value10f);
                valueGrav10.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(valueGrav10);
                // LABEL, TOTAL_IVA_10% 
                PdfPCell lblIva10 = new PdfPCell(new Phrase("IVA 10%: ", FontFactory.getFont("arial", 10, Font.BOLD)));
                lblIva10.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblIva10.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblIva10.setPaddingTop(value10f);
                lblIva10.setPaddingRight(value20f);
                lblIva10.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(lblIva10);
                // VALUE, TOTAL_IVA_10% 
                PdfPCell valueIva10 = new PdfPCell(new Phrase(formatear.format(VAR_TOTAL_IVA10), FontFactory.getFont("arial", 11, Font.NORMAL)));
                valueIva10.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueIva10.setHorizontalAlignment(Element.ALIGN_LEFT);
                valueIva10.setPaddingTop(value10f);
                valueIva10.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(valueIva10);
                // LABEL, TOTAL_GRAVADA_5% 
                PdfPCell lblGrav5 = new PdfPCell(new Phrase("Gravada 5%: ", FontFactory.getFont("arial", 10, Font.BOLD)));
                lblGrav5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblGrav5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblGrav5.setPaddingTop(value10f);
                lblGrav5.setPaddingRight(value20f);
                lblGrav5.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(lblGrav5);
                // VALUE, TOTAL_GRAVADA_5%
                PdfPCell valueGrav5 = new PdfPCell(new Phrase(formatear.format(VAR_TOTAL_GRAV5), FontFactory.getFont("arial", 11, Font.NORMAL)));
                valueGrav5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueGrav5.setHorizontalAlignment(Element.ALIGN_LEFT);
                valueGrav5.setPaddingTop(value10f);
                valueGrav5.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(valueGrav5);
                // LABEL, TOTAL_IVA_5 
                PdfPCell lblIva5 = new PdfPCell(new Phrase("IVA 5%: ", FontFactory.getFont("arial", 10, Font.BOLD)));
                lblIva5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblIva5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblIva5.setPaddingTop(value10f);
                lblIva5.setPaddingRight(value20f);
                lblIva5.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(lblIva5);
                // VALUE, TOTAL_IVA_5 
                PdfPCell valueIva5 = new PdfPCell(new Phrase(formatear.format(VAR_TOTAL_IVA5), FontFactory.getFont("arial", 11, Font.NORMAL)));
                valueIva5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueIva5.setHorizontalAlignment(Element.ALIGN_LEFT);
                valueIva5.setPaddingTop(value10f);
                valueIva5.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(valueIva5);
                // LABEL, TOTAL_IVA 
                PdfPCell lblIva = new PdfPCell(new Phrase("Total IVA: ", FontFactory.getFont("arial", 10, Font.BOLD)));
                lblIva.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblIva.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblIva.setPaddingTop(value10f);
                lblIva.setPaddingRight(value20f);
                lblIva.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(lblIva);
                // VALUE, TOTAL_IVA 
                PdfPCell valueIva = new PdfPCell(new Phrase(formatear.format(VAR_TOTAL_IVA), FontFactory.getFont("arial", 11, Font.NORMAL)));
                valueIva.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueIva.setHorizontalAlignment(Element.ALIGN_LEFT);
                valueIva.setPaddingTop(value10f);
                valueIva.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(valueIva);
                // LABEL, TOTAL_FACTURA 
                PdfPCell lblTotalIva = new PdfPCell(new Phrase("Total: ", FontFactory.getFont("arial", 11, Font.BOLD)));
                lblTotalIva.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblTotalIva.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblTotalIva.setPaddingTop(value10f);
                lblTotalIva.setPaddingRight(value20f);
                lblTotalIva.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(lblTotalIva);
                // VALUE, TOTAL_FACTURA 
                PdfPCell valueTotalIva = new PdfPCell(new Phrase(formatear.format(VAR_TOTAL), FontFactory.getFont("arial", 11, Font.NORMAL)));
                valueTotalIva.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueTotalIva.setHorizontalAlignment(Element.ALIGN_LEFT);
                valueTotalIva.setPaddingTop(value10f);
                valueTotalIva.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(valueTotalIva);
                
                tablaTotales.setSpacingBefore(15);
                documento.add(tablaTotales);
                
            
            documento.close();
            
            // Hay que configurar las cabeceras para que el navegador detecte que es un PDF 
            response.setHeader("Expires", "0");
            response.setHeader("Cache - Control", "must - revalidate, post - check = 0, pre - check = 0");
            response.setHeader("Pragma", "public ");
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