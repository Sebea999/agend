/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.modelo.pdf;

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
@WebServlet(name="crearPdfFacturaLista", urlPatterns="/lista_facturas_pdf")
public class CrearPdfFacturaLista extends HttpServlet {
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
        doPost(request, response);
    }
    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            // -----------------------------------------------------------------------------------------------------
            //              CODIGO PARA CREAR EL PDF 
            // -----------------------------------------------------------------------------------------------------
            System.out.println(".");
            System.out.println(".");
            System.out.println("..");
            System.out.println("--------------------    CREAR_PDF_SERVLET()    --------------------");
            System.out.println("..");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
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
            if (((List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("PDF_LISTA_RPT_FACTURA")) != null) {
                listaFactura = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("PDF_LISTA_RPT_FACTURA");
            } else {
                String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
                System.out.println(".   _PDF__ID_USUARIO_PERSONA:    :"+SESION_IDUSER);
                com.agend.modelo.ModelPerfil modeloPerfil = new com.agend.modelo.ModelPerfil();
                String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
                System.out.println(".   _PDF__ID_PERFIL_USER:        :"+IDPERFIL_USER);
                
                // NRO PAG ACTUAL A MOSTRAR 
                String NRO_PAG;
                if (String.valueOf(request.getParameter("PDFNPA")).isEmpty() || request.getParameter("PDFNPA") == null) {
//                if (String.valueOf(sesionDatosUsuario.getAttribute("PDF_FACT_LISTA_VAR_AUX_NRO_PAG")).isEmpty() || sesionDatosUsuario.getAttribute("PDF_FACT_LISTA_VAR_AUX_NRO_PAG") == null) {
                    NRO_PAG = "1";
                } else {
                    NRO_PAG = (String) request.getParameter("PDFNPA");
//                    NRO_PAG = (String) sesionDatosUsuario.getAttribute("PDF_FACT_LISTA_VAR_AUX_NRO_PAG");
                }
                System.out.println("_    ___PDF____NRO_PAG_ACTUAL:     :"+NRO_PAG);
                // NRO DE REGISTRO A MOSTRAR 
                String NRO_REG_MOSTRAR;
                if (String.valueOf(request.getParameter("PDFNCRM")).isEmpty() || request.getParameter("PDFNCRM") == null) { // SI SE ENCUENTRA NULL ENTONCES MOSTRARIA EL MENOR NUMERO DE REGISTROS ESTABLECIDOS EN EL METODO DE INICIO DE SESION 
//                if (String.valueOf(sesionDatosUsuario.getAttribute("PDF_FACT_LISTA_VAR_AUX_NRO_REG")).isEmpty() || sesionDatosUsuario.getAttribute("PDF_FACT_LISTA_VAR_AUX_NRO_REG") == null) { // SI SE ENCUENTRA NULL ENTONCES MOSTRARIA EL MENOR NUMERO DE REGISTROS ESTABLECIDOS EN EL METODO DE INICIO DE SESION 
                    NRO_REG_MOSTRAR = metodosIniSes.minNroCbxCantFil();
                } else {
                    NRO_REG_MOSTRAR = (String) request.getParameter("PDFNCRM");
//                    NRO_REG_MOSTRAR = (String) sesionDatosUsuario.getAttribute("PDF_FACT_LISTA_VAR_AUX_NRO_REG");
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
            
            documento.addTitle("Reporte de Factura");
            String empresa = metodosIniSes.devolverEmpresa();
            Paragraph titulo = new Paragraph(empresa, FontFactory.getFont("arial", 16, Font.BOLD));
            titulo.setAlignment(1);
            titulo.setSpacingAfter(value10f);
            documento.add(titulo);
            
            Paragraph subtitulo = new Paragraph("Lista de Facturas", FontFactory.getFont("arial", 15, Font.BOLD));
            subtitulo.setAlignment(1);
            subtitulo.setSpacingAfter(value5f);
            documento.add(subtitulo);
            
            PdfPTable tablaDetalle = new PdfPTable(4);
            tablaDetalle.setTotalWidth(400);
            float[] medidaCeldaDet = {100, 140, 80, 80};
            tablaDetalle.setWidths(medidaCeldaDet);
            tablaDetalle.setWidthPercentage(100);
            tablaDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            // COLUMNA NRO_FACTURA 
            PdfPCell colNroFactura = new PdfPCell(new Phrase("Nro. Factura", FontFactory.getFont("arial", 12, Font.BOLD)));
            colNroFactura.setVerticalAlignment(1);
            colNroFactura.setHorizontalAlignment(1);
            colNroFactura.setPadding(value5f);
            colNroFactura.setPaddingTop(value5f);
            colNroFactura.setPaddingBottom(value5f);
            tablaDetalle.addCell(colNroFactura);
            // COLUMNA CLIENTE 
            PdfPCell colCliente = new PdfPCell(new Phrase("Cliente", FontFactory.getFont("arial", 12, Font.BOLD)));
            colCliente.setVerticalAlignment(1);
            colCliente.setHorizontalAlignment(1);
            colCliente.setPadding(value5f);
            colCliente.setPaddingTop(value5f);
            colCliente.setPaddingBottom(value5f);
            tablaDetalle.addCell(colCliente);
            // COLUMNA FECHA_FACTURA 
            PdfPCell colFechaFactura = new PdfPCell(new Phrase("Fecha Factura", FontFactory.getFont("arial", 12, Font.BOLD)));
            colFechaFactura.setVerticalAlignment(1);
            colFechaFactura.setHorizontalAlignment(1);
            colFechaFactura.setPadding(value5f);
            colFechaFactura.setPaddingTop(value5f);
            colFechaFactura.setPaddingBottom(value5f);
            tablaDetalle.addCell(colFechaFactura);
            // COLUMNA TOTAL_FACTURA  
            PdfPCell colTotal = new PdfPCell(new Phrase("Total", FontFactory.getFont("arial", 12, Font.BOLD)));
            colTotal.setVerticalAlignment(1);
            colTotal.setHorizontalAlignment(1);
            colTotal.setPadding(value5f);
            colTotal.setPaddingTop(value5f);
            colTotal.setPaddingBottom(value5f);
            tablaDetalle.addCell(colTotal);
            
            for (BeanFacturaCab detalleDatos : listaFactura) {
                String nrofactura = detalleDatos.getOF_NROFACTURA();
                String idcliente = String.valueOf(detalleDatos.getOF_IDCLIENTE());
                String fechafactura = detalleDatos.getOF_FECHAFACTURA();
                String totalfactura = formatear.format(Double.parseDouble(detalleDatos.getOF_TOTAL_FACTURA()));
                
                // RECUPERO LOS DATOS DEL CLIENTE 
                datosPersona = metodosPersona.datosBasicosPersona(datosPersona, idcliente);
                String cinro = datosPersona.getRP_CINRO();
                String cliente = datosPersona.getRP_NOMBRE()+" "+datosPersona.getRP_APELLIDO();
                
                System.out.println("------------  DATOS  -------------");
                System.out.println("__  NRO_FACTURA:      :"+nrofactura);
                System.out.println("__   ID_CLIENTE:      :"+idcliente);
                System.out.println("__      CLIENTE:      :"+cliente);
                System.out.println("__       CI_NRO:      :"+cinro);
                System.out.println("__FECHA_FACTURA:      :"+fechafactura);
                System.out.println("__TOTAL_FACTURA:      :"+totalfactura);
                System.out.println("------------ ------- -------------");
                
                // NRO_FACTURA
                PdfPCell cellNroFactura = new PdfPCell(new Phrase(nrofactura, FontFactory.getFont("arial", 11, Font.NORMAL)));
                cellNroFactura.setUseAscender(true);
                cellNroFactura.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellNroFactura.setHorizontalAlignment(Element.ALIGN_LEFT);
                cellNroFactura.setPadding(value5f);
                cellNroFactura.setPaddingLeft(value10f);
                cellNroFactura.setBorder(Rectangle.BOTTOM);
                cellNroFactura.setBorderColor(BaseColor.GRAY);
                tablaDetalle.addCell(cellNroFactura);
                // CLIENTE 
                PdfPCell cellCliente = new PdfPCell(new Phrase(cliente, FontFactory.getFont("arial", 10, Font.NORMAL)));
                cellCliente.setUseAscender(true);
                cellCliente.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellCliente.setHorizontalAlignment(Element.ALIGN_LEFT);
                cellCliente.setPadding(value5f);
                cellCliente.setBorder(Rectangle.BOTTOM);
                cellCliente.setBorderColor(BaseColor.GRAY);
                tablaDetalle.addCell(cellCliente);
                // FECHA_FACTURA
                PdfPCell cellFechaFact = new PdfPCell(new Phrase(fechafactura, FontFactory.getFont("arial", 11, Font.NORMAL)));
                cellFechaFact.setUseAscender(true);
                cellFechaFact.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellFechaFact.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellFechaFact.setPadding(value5f);
                cellFechaFact.setBorder(Rectangle.BOTTOM);
                cellFechaFact.setBorderColor(BaseColor.GRAY);
                tablaDetalle.addCell(cellFechaFact);
                // TOTAL_FACTURA
                PdfPCell cellTotalFact = new PdfPCell(new Phrase(totalfactura, FontFactory.getFont("arial", 11, Font.NORMAL)));
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
            
            documento.add(Chunk.NEWLINE);
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