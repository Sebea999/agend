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
import java.util.List;
import com.agend.javaBean.BeanCuentaCliente;
import com.agend.javaBean.BeanPersona;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.agend.modelo.ModelCuentaCliente;
import com.agend.modelo.ModelInicioSesion;
import com.agend.modelo.ModelPersona;

/**
 *
 * @author RYUU
 */
@WebServlet(name="crearPdfCuentaPacienteLista", urlPatterns="/lista_cuentas_pdf")
public class CrearPdfCuentaClienteLista extends HttpServlet {
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Document documento = new Document(PageSize.LEGAL);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(documento, baos);
            
            // -----------------------------------------------------------------------------------------------------
            //              CODIGO PARA CREAR EL PDF 
            // -----------------------------------------------------------------------------------------------------
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println("..");
            System.out.println("--------------------    CREAR_PDF_SERVLET()    --------------------");
            System.out.println("..");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            // FRAGMENTO DE CODIGO PARA GUARDAR EL ARCHIVO PDF 
//                try {
//                    PdfWriter.getInstance(documento, new FileOutputStream("C:\\Empeno\\prueba_plantilla.pdf"));
//                } catch (FileNotFoundException fileNotFoundException) { // NORMALMENTE DARIA ESTE ERROR CUANDO EL ARCHIVO ESTE ABIERTO O LO ESTE USANDO OTRO PROCESO // SI YA ESTA CREADO PERO NO ESTA SIENDO UTILIZADO NO VA A DAR ERROR, VA A SOBREESCRIBIR 
//                    System.out.println("No such file was found to generate the PDF "
//                            + "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
//                }
                // ABRO EL DOCUMENTO PARA PODER IR CARGANDO CON EL CONTENIDO 
            documento.open();
            
            // INSTANCIO LA SESION DONDE PUEDO RECUPERAR DATOS DEL USUARIO 
            HttpSession sesionDatosUsuario = request.getSession();
            ModelInicioSesion metodosIniSes = new ModelInicioSesion();
            ModelPersona metodosPersona = new ModelPersona();
            ModelCuentaCliente metodosCuentaCliente = new ModelCuentaCliente();
            List<BeanCuentaCliente> listaCuentas;
            
            // OBS.: RECUPERO DE LA SESION PORQUE LA GRILLA SE PUEDE FILTRAR, Y ENTONCES ES MAS FACIL QUE AL CARGAR EL FILTRO YA CARGUE LA LISTA A LA SESION Y SI ESTUVIESE VACIO LA LISTA DE LA SESION, ENTONCES ES PORQUE SE LIMPIO EL FILTRO O NO SE FILTRO Y ENTONCES RECUPERARIA LOS DATOS DEL CARGA_GRILLA 
            if (sesionDatosUsuario.getAttribute("PDF_LISTA_RPT_CTA_CLIE") == null) {
                System.out.println("_1_____PDF_____ if _____LISTA DE LA SESION NULA__________");
                // NRO PAG ACTUAL A MOSTRAR 
                String NRO_PAG;
                if (String.valueOf(request.getParameter("PDFNPA")).isEmpty() || request.getParameter("PDFNPA") == null) {
//                if (String.valueOf(sesionDatosUsuario.getAttribute("PDF_RPT_CTAPAC_VAR_AUX_NRO_PAG")).isEmpty() || sesionDatosUsuario.getAttribute("PDF_RPT_CTAPAC_VAR_AUX_NRO_PAG") == null) {
                    NRO_PAG = "1";
                } else {
                    NRO_PAG = (String) request.getParameter("PDFNPA");
//                    NRO_PAG = (String) sesionDatosUsuario.getAttribute("PDF_RPT_CTAPAC_VAR_AUX_NRO_PAG");
                }
                System.out.println("_    ___PDF____NRO_PAG_ACTUAL:     :"+NRO_PAG);
                // NRO DE REGISTRO A MOSTRAR 
                String NRO_REG_MOSTRAR;
                if (String.valueOf(request.getParameter("PDFNCRM")).isEmpty() || request.getParameter("PDFNCRM") == null) { // SI SE ENCUENTRA NULL ENTONCES MOSTRARIA EL MENOR NUMERO DE REGISTROS ESTABLECIDOS EN EL METODO DE INICIO DE SESION 
//                if (String.valueOf(sesionDatosUsuario.getAttribute("PDF_RPT_CTAPAC_VAR_AUX_NRO_REG")).isEmpty() || sesionDatosUsuario.getAttribute("PDF_RPT_CTAPAC_VAR_AUX_NRO_REG") == null) { // SI SE ENCUENTRA NULL ENTONCES MOSTRARIA EL MENOR NUMERO DE REGISTROS ESTABLECIDOS EN EL METODO DE INICIO DE SESION 
                    NRO_REG_MOSTRAR = metodosIniSes.minNroCbxCantFil();
                } else {
                    NRO_REG_MOSTRAR = (String) request.getParameter("PDFNCRM");
//                    NRO_REG_MOSTRAR = (String) sesionDatosUsuario.getAttribute("PDF_RPT_CTAPAC_VAR_AUX_NRO_REG");
                }
                System.out.println("_    ___PDF____NRO_REG_MOSTRAR:     :"+NRO_REG_MOSTRAR);
                
//                String NRO_PAG = ""; // OBTENGO EL NUMERO DE PAGINA ACTUAL QUE LA GRILLA VA A MOSTRAR 
//                if (sesionDatosUsuario.getAttribute("PAG_RPT_CTAPAC_LISTA_ACTUAL") == null) {
//                    NRO_PAG = "1";
//                } else {
//                    NRO_PAG = (String) sesionDatosUsuario.getAttribute("PAG_RPT_CTAPAC_LISTA_ACTUAL");
//                }
//                System.out.println("_   _PDF____NRO_PAG_ACTUAL:     :"+NRO_PAG);
//                String cant_min_fija = metodosIniSes.minNroCbxCantFil(); // NRO DE REGISTROS A MOSTRAR EN LA GRILLA QUE MUESTRA EL COMBO POR DEFECTO 
                listaCuentas = metodosCuentaCliente.cargar_grilla_paginacion_rtp_cta_pac(sesionDatosUsuario, NRO_PAG, NRO_REG_MOSTRAR);
            } else {
                System.out.println("_2_____PDF_____ else _____LISTA DE LA SESION CARGADA__________");
                listaCuentas = (List<BeanCuentaCliente>) sesionDatosUsuario.getAttribute("PDF_LISTA_RPT_CTA_CLIE");
            }
            
            // CARGO EL TITULO DE LA PAGINA ------- 
            documento.addTitle("Reporte Cuenta Cliente");
            // CARGO EL TITULO DE LA HOJA --------- 
            String empresa = metodosIniSes.devolverEmpresa();
            Paragraph titulo = new Paragraph(empresa, FontFactory.getFont("arial", 16, Font.BOLD));
            titulo.setAlignment(1);
            titulo.setSpacingAfter(10f);
            documento.add(titulo);
            // CARGO EL SUBTITULO ------------- 
            Paragraph subtitulo = new Paragraph("Lista de Cuentas", FontFactory.getFont("arial", 15, Font.BOLD));
            subtitulo.setAlignment(1);
            subtitulo.setSpacingAfter(10f);
            documento.add(subtitulo);
            
            
        // DETALLE -------------- ----------------- ---------------- -------------- ----------------- ----------------
            PdfPTable tablaListaCli = new PdfPTable(4);
            tablaListaCli.setTotalWidth(360);
            float[] medidaCeldaDet = {60, 170, 60, 70};
            tablaListaCli.setWidths(medidaCeldaDet);
            tablaListaCli.setHorizontalAlignment(Element.ALIGN_CENTER);
            // COLUMNAS --------- ----------- ------------ 
            float valor5f = 5f;
            // COLUMNA IDCLIENTE 
            PdfPCell colIdClie = new PdfPCell(new Phrase("Cód.", FontFactory.getFont("arial", 12, Font.BOLD)));
            colIdClie.setVerticalAlignment(1);
            colIdClie.setHorizontalAlignment(1);
            colIdClie.setPadding(valor5f);
            colIdClie.setPaddingTop(valor5f);
            colIdClie.setPaddingBottom(valor5f);
            tablaListaCli.addCell(colIdClie);
            // COLUMNA CLIENTE 
            PdfPCell colCliente = new PdfPCell(new Phrase("Paciente", FontFactory.getFont("arial", 12, Font.BOLD)));
            colCliente.setVerticalAlignment(1);
            colCliente.setHorizontalAlignment(1);
            colCliente.setPadding(valor5f);
            colCliente.setPaddingTop(valor5f);
            colCliente.setPaddingBottom(valor5f);
            tablaListaCli.addCell(colCliente);
            // COLUMNA NRO CI 
            PdfPCell colNroCi = new PdfPCell(new Phrase("Nro. CI", FontFactory.getFont("arial", 12, Font.BOLD)));
            colNroCi.setVerticalAlignment(1);
            colNroCi.setHorizontalAlignment(1);
            colNroCi.setPadding(valor5f);
            colNroCi.setPaddingTop(valor5f);
            colNroCi.setPaddingBottom(valor5f);
            tablaListaCli.addCell(colNroCi);
            // COLUMNA SALDO 
            PdfPCell colSaldo = new PdfPCell(new Phrase("Saldo", FontFactory.getFont("arial", 12, Font.BOLD)));
            colSaldo.setVerticalAlignment(1);
            colSaldo.setHorizontalAlignment(1);
            colSaldo.setPadding(valor5f);
            colSaldo.setPaddingTop(valor5f);
            colSaldo.setPaddingBottom(valor5f);
            tablaListaCli.addCell(colSaldo);
            
            for (BeanCuentaCliente detalleCtasCli : listaCuentas) {
                String idCliente = detalleCtasCli.getCC_IDPERSONA();
                System.out.println("__DET__ID_CLIENTE:      :"+idCliente);
                BeanPersona datosPersona = new BeanPersona();
                datosPersona = metodosPersona.datosBasicosPersona(datosPersona, idCliente);
                String cliente = datosPersona.getRP_NOMBRE()+" "+datosPersona.getRP_APELLIDO();
                String cinro = datosPersona.getRP_CINRO();
                String saldo = detalleCtasCli.getCC_SALDO();
                System.out.println("-------------__DATOS__----------------");
                System.out.println("___ID_CLIE:     :"+idCliente);
                System.out.println("___CLIENTE:     :"+cliente);
                System.out.println("____NRO_CI:     :"+cinro);
                System.out.println("_____SALDO:     :"+saldo);
                System.out.println("--------------------------------------");
                
                // ID_CLIENTE 
                PdfPCell cellIdClie = new PdfPCell(new Phrase(idCliente));
                cellIdClie.setUseAscender(true);
                cellIdClie.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellIdClie.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellIdClie.setPadding(valor5f);
                cellIdClie.setBorder(Rectangle.BOTTOM);
                cellIdClie.setBorderColor(BaseColor.GRAY);
                tablaListaCli.addCell(cellIdClie);
                // CLIENTE 
                PdfPCell cellCliente = new PdfPCell(new Phrase(cliente));
                cellCliente.setUseAscender(true);
                cellCliente.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellCliente.setHorizontalAlignment(Element.ALIGN_LEFT);
                cellCliente.setPadding(valor5f);
                cellCliente.setBorder(Rectangle.BOTTOM);
                cellCliente.setBorderColor(BaseColor.GRAY);
                tablaListaCli.addCell(cellCliente);
                // CINRO 
                PdfPCell cellCinro = new PdfPCell(new Phrase(cinro));
                cellCinro.setUseAscender(true);
                cellCinro.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellCinro.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellCinro.setPadding(valor5f);
                cellCinro.setBorder(Rectangle.BOTTOM);
                cellCinro.setBorderColor(BaseColor.GRAY);
                tablaListaCli.addCell(cellCinro);
                // SALDO 
                PdfPCell cellSaldo = new PdfPCell(new Phrase(saldo));
                cellSaldo.setUseAscender(true);
                cellSaldo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellSaldo.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellSaldo.setPadding(valor5f);
                cellSaldo.setBorder(Rectangle.BOTTOM);
                cellSaldo.setBorderColor(BaseColor.GRAY);
                tablaListaCli.addCell(cellSaldo);
            } // end for 
            tablaListaCli.setSpacingBefore(15);
            documento.add(tablaListaCli);
            
            documento.add(Chunk.NEWLINE);
            documento.close();
            
            // Hay que configurar las cabeceras para que el navegador detecte que es un PDF 
            response.setHeader("Expires", "0");
            response.setHeader("Cache - Control", "must - revalidate, post - check = 0, pre - check = 0");
            response.setHeader("Pragma", "public ");
            // Configuramos el content type 
            response.setContentType("application/pdf");
            // Tamaño 
            response.setContentLength (baos.size());
            // Escribir el ByteArrayOutputStream a el ServletOutputSream 
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
    } // END DO_POST 
    
    
} // END CLASS 