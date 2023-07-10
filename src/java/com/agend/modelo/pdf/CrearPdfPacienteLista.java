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
import com.agend.javaBean.BeanPersona;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.agend.modelo.ModelInicioSesion;
import com.agend.modelo.ModelPersona;

/**
 *
 * @author RYUU
 */
@WebServlet(name="crearPdfPacienteLista", urlPatterns="/lista_paciente_pdf")
public class CrearPdfPacienteLista extends HttpServlet {
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        doPost(request, response);
    }
    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        try {
            // INSTANCIO UN DOCUMENTO DONDE CREARE EL PDF 
            Document documento = new Document(PageSize.LEGAL);
            // LE DEFINO LOS MARGENES A LA HOJA CON CERO A LOS COSTADOS PARA DARLE MAS ESPACIO A LA TABLA 
            documento.setMargins(0, 0, 20, 20);
            // Paso 2: Creamos un ByteArrayOutputStream
            // todo lo que se escriba en el documento
            // se escribe tambien en el ByteArrayOutputStream
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(documento, baos);
            
            // -----------------------------------------------------------------------------------------------------
            //              CODIGO PARA CREAR EL PDF 
            // -----------------------------------------------------------------------------------------------------
            System.out.println(".");
            System.out.println(".");
            System.out.println("..");
            System.out.println("--------------------    CREAR_PDF_SERVLET()____PACIENTE_LIST____    --------------------");
            System.out.println("..");
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
            List<BeanPersona> listaDatos;
            
            // OBS.: RECUPERO DE LA SESION PORQUE LA GRILLA SE PUEDE FILTRAR, Y ENTONCES ES MAS FACIL QUE AL CARGAR EL FILTRO YA CARGUE LA LISTA A LA SESION Y SI ESTUVIESE VACIO LA LISTA DE LA SESION, ENTONCES ES PORQUE SE LIMPIO EL FILTRO O NO SE FILTRO Y ENTONCES RECUPERARIA LOS DATOS DEL CARGA_GRILLA 
            if (sesionDatosUsuario.getAttribute("PDF_LISTA_RPT_PACIENTE") == null) {
                System.out.println("_1_____PDF_____ if _____LISTA DE LA SESION NULA__________");
                // NRO PAG ACTUAL A MOSTRAR 
                String NRO_PAG;
                if (String.valueOf(request.getParameter("PDFNPA")).isEmpty() || request.getParameter("PDFNPA") == null) {
//                if (String.valueOf(sesionDatosUsuario.getAttribute("PDF_RPT_PAC_VAR_AUX_NRO_PAG")).isEmpty() || sesionDatosUsuario.getAttribute("PDF_RPT_PAC_VAR_AUX_NRO_PAG") == null) {
                    System.out.println("___NP_IF___");
                    NRO_PAG = "1";
                } else {
                    System.out.println("___NP_ELSE___");
                    NRO_PAG = (String) request.getParameter("PDFNPA"); // VARIABLE DEL METODO 
//                    NRO_PAG = (String) sesionDatosUsuario.getAttribute("PAG_RPT_PAC_LISTA_ACTUAL"); // VARIABLE DEL METODO 
//                    NRO_PAG = (String) sesionDatosUsuario.getAttribute("PDF_RPT_PAC_VAR_AUX_NRO_PAG"); // VARIABLE AUXILIAR DE LA VARIABLE 
                }
                System.out.println("_    ___PDF____NRO_PAG_ACTUAL:     :"+NRO_PAG);
                // NRO DE REGISTRO A MOSTRAR 
                String NRO_REG_MOSTRAR;
                if (String.valueOf(request.getParameter("PDFNCRM")).isEmpty() || request.getParameter("PDFNCRM") == null) { // SI SE ENCUENTRA NULL ENTONCES MOSTRARIA EL MENOR NUMERO DE REGISTROS ESTABLECIDOS EN EL METODO DE INICIO DE SESION 
//                if (String.valueOf(sesionDatosUsuario.getAttribute("PDF_RPT_PAC_VAR_AUX_NRO_REG")).isEmpty() || sesionDatosUsuario.getAttribute("PDF_RPT_PAC_VAR_AUX_NRO_REG") == null) { // SI SE ENCUENTRA NULL ENTONCES MOSTRARIA EL MENOR NUMERO DE REGISTROS ESTABLECIDOS EN EL METODO DE INICIO DE SESION 
                    System.out.println("___NP_IF___");
                    NRO_REG_MOSTRAR = metodosIniSes.minNroCbxCantFil();
                } else {
                    System.out.println("___NR_ELSE___");
                    NRO_REG_MOSTRAR = (String) request.getParameter("PDFNCRM");
//                    NRO_REG_MOSTRAR = (String) sesionDatosUsuario.getAttribute("PDF_RPT_PAC_VAR_AUX_NRO_REG"); // VARIABLE AUXILIAR DE LA VARIABLE 
                }
                System.out.println("_    ___PDF____NRO_REG_MOSTRAR:     :"+NRO_REG_MOSTRAR);
                
//                // OBTENGO EL NUMERO DE PAGINA ACTUAL QUE LA GRILLA VA A MOSTRAR 
//                String NRO_PAG = "";
//                if (sesionDatosUsuario.getAttribute("PAG_RPT_PAC_LISTA_ACTUAL") == null) {
//                    NRO_PAG = "1";
//                } else {
//                    NRO_PAG = (String) sesionDatosUsuario.getAttribute("PAG_RPT_PAC_LISTA_ACTUAL");
//                }
//                System.out.println("_   _JSP____NRO_PAG_ACTUAL:     :"+NRO_PAG);
//                String cant_min_fija = metodosIniSes.minNroCbxCantFil(); // NRO DE REGISTROS A MOSTRAR EN LA GRILLA QUE MUESTRA EL COMBO POR DEFECTO 
                listaDatos = metodosPersona.cargar_grilla_paginacion_rpt_pac(sesionDatosUsuario, NRO_PAG, NRO_REG_MOSTRAR);
            } else {
                System.out.println("_2_____PDF_____ else _____LISTA DE LA SESION CARGADA__________");
                listaDatos = (List<BeanPersona>) sesionDatosUsuario.getAttribute("PDF_LISTA_RPT_PACIENTE");
            }
            
            // CARGO EL TITULO DE LA PAGINA 
            documento.addTitle("Reporte Paciente");
            // CARGO EL TITULO DE LA HOJA -----------
            String empresa = metodosIniSes.devolverEmpresa();
            Paragraph titulo = new Paragraph(empresa, FontFactory.getFont("arial", 16, Font.BOLD));
            titulo.setAlignment(1);// ALINEO EL TITULO  // center 
            titulo.setSpacingAfter(10f);// ESPACIO VERTICAL DEL TITULO CON EL PROXIMO ELEMENTO 
            documento.add(titulo);
//            documento.add(Chunk.NEWLINE); // LINEA EN BLANCO // ENTER 
            // CARGO EL SUBTITULO ---------
            Paragraph subtitulo = new Paragraph("Lista de Pacientes", FontFactory.getFont("arial", 15, Font.BOLD));
            subtitulo.setAlignment(1); // ALINEO EL TITULO // center 
            subtitulo.setSpacingAfter(10f);// ESPACIO VERTICAL DEL TITULO CON EL PROXIMO ELEMENTO 
            documento.add(subtitulo);
            
            // DETALLE -------------- ----------------- ---------------- -------------- ----------------- ----------------
            PdfPTable tableDetalle = new PdfPTable(5);
            tableDetalle.setTotalWidth(500);
            float[] medidaCeldaDet = {40, 170, 60, 150, 80};
            tableDetalle.setWidths(medidaCeldaDet);
            tableDetalle.setHorizontalAlignment(Element.ALIGN_CENTER);
            // CARGO LAS COLUMNAS DE LA TABLA --------------
            // COLUMNA DE ID_CLIENTE 
            PdfPCell colIdCliente = new PdfPCell(new Phrase("Cód.", FontFactory.getFont("arial", 12, Font.BOLD)));
            colIdCliente.setVerticalAlignment(1); // CENTRADO VERTICAL 
            colIdCliente.setHorizontalAlignment(1); // CENTRADO HORIZONTAL 
            colIdCliente.setPadding(5f);
            colIdCliente.setPaddingBottom(10f);
//            colIdCliente.setBorder(Rectangle.BOTTOM);
            tableDetalle.addCell(colIdCliente); // AÑADO A LA TABLA LA CELDA 
            // COLUMNA DE NOMBRE_APELLIDO 
            PdfPCell colCliente = new PdfPCell(new Phrase("Paciente", FontFactory.getFont("arial", 12, Font.BOLD)));
            colCliente.setVerticalAlignment(1); // CENTRADO VERTICAL 
//            colCliente.setHorizontalAlignment(1); // CENTRADO HORIZONTAL 
            colCliente.setPadding(5f);
            colCliente.setPaddingBottom(10f);
//            colCliente.setBorder(Rectangle.BOTTOM);
            tableDetalle.addCell(colCliente); // AÑADO A LA TABLA LA CELDA 
            // COLUMNA DE CINRO 
            PdfPCell colCinro = new PdfPCell(new Phrase("Nro. CI", FontFactory.getFont("arial", 12, Font.BOLD)));
            colCinro.setVerticalAlignment(1); // CENTRADO VERTICAL 
            colCinro.setHorizontalAlignment(1); // CENTRADO HORIZONTAL 
            colCinro.setPadding(5f);
            colCinro.setPaddingBottom(10f);
//            colCinro.setBorder(Rectangle.BOTTOM);
            tableDetalle.addCell(colCinro); // AÑADO A LA TABLA LA CELDA 
            // COLUMNA DE DIRECCION 
            PdfPCell colDireccion = new PdfPCell(new Phrase("Dirección", FontFactory.getFont("arial", 12, Font.BOLD)));
            colDireccion.setVerticalAlignment(1); // CENTRADO VERTICAL 
            colDireccion.setHorizontalAlignment(1); // CENTRADO HORIZONTAL 
            colDireccion.setPadding(5f);
            colDireccion.setPaddingBottom(10f);
//            colDireccion.setBorder(Rectangle.BOTTOM);
            tableDetalle.addCell(colDireccion); // AÑADO A LA TABLA LA CELDA 
            // COLUMNA DE TELEFONO 
            PdfPCell colTelefono = new PdfPCell(new Phrase("Teléfono", FontFactory.getFont("arial", 12, Font.BOLD)));
            colTelefono.setVerticalAlignment(1); // CENTRADO VERTICAL 
            colTelefono.setHorizontalAlignment(1); // CENTRADO HORIZONTAL 
            colTelefono.setPadding(5f);
            colTelefono.setPaddingBottom(10f);
//            colTelefono.setBorder(Rectangle.BOTTOM);
            tableDetalle.addCell(colTelefono); // AÑADO A LA TABLA LA CELDA 
            
            // CARGO LAS CELDAS DE LA TABLA -----------------
            for(BeanPersona detCliente : listaDatos) {
                String idCliente = detCliente.getRP_IDPERSONA();
                String cliente = detCliente.getRP_NOMBRE()+" "+detCliente.getRP_APELLIDO();
                String cinro = detCliente.getRP_CINRO();
                String direccion = detCliente.getRP_DIRECCION();
                if (direccion == null || direccion.isEmpty()) {
                    direccion = "";
                }
                String telefono = detCliente.getRP_TELFMOVIL();
                if (telefono == null || telefono.isEmpty()) {
                    telefono = "";
                }
                
                // CELDA DE LA COLUMNA ID_CLIENTE ---
                PdfPCell cellIdCliente = new PdfPCell(new Phrase(idCliente, FontFactory.getFont("arial", 11, Font.NORMAL)));
                cellIdCliente.setUseAscender(true); // HABILITO EL AJUSTE DE LA ALTURA DE LA CELDA 
                cellIdCliente.setVerticalAlignment(Element.ALIGN_MIDDLE); // ALINEACION VERTICAL EN LA CELDA 
                cellIdCliente.setHorizontalAlignment(Element.ALIGN_CENTER); // ALINEACION HORIZONTAL EN LA CELDA 
                cellIdCliente.setPadding(5f); // LE AÑADO UN PADDING A LA CELDA Y AUTOMATICAMENTE AÑADE A LAS OTRAS CELDAS DE LAS OTRAS COLUMNAS TAMBIEN, EN CASO DE QUE NO LO HICIERA ENTONCES CADA INSTANCIA DE CADA CELDA TENDRA QUE TENER ESTA LINEA DE CODIGO 
                cellIdCliente.setBorder(Rectangle.BOTTOM);
                cellIdCliente.setBorderColor(BaseColor.GRAY);
                tableDetalle.addCell(cellIdCliente);
                // CELDA DE LA COLUMNA CLIENTE ---
                PdfPCell cellCliente = new PdfPCell(new Phrase(cliente, FontFactory.getFont("arial", 10, Font.NORMAL)));
                cellCliente.setUseAscender(true); // HABILITO EL AJUSTE DE LA ALTURA DE LA CELDA 
                cellCliente.setVerticalAlignment(Element.ALIGN_MIDDLE); // ALINEACION VERTICAL EN LA CELDA 
                cellCliente.setHorizontalAlignment(Element.ALIGN_LEFT); // ALINEACION HORIZONTAL EN LA CELDA 
                cellCliente.setPadding(5f); // LE AÑADO UN PADDING A LA CELDA Y AUTOMATICAMENTE AÑADE A LAS OTRAS CELDAS DE LAS OTRAS COLUMNAS TAMBIEN, EN CASO DE QUE NO LO HICIERA ENTONCES CADA INSTANCIA DE CADA CELDA TENDRA QUE TENER ESTA LINEA DE CODIGO 
                cellCliente.setBorder(Rectangle.BOTTOM);
                cellCliente.setBorderColor(BaseColor.GRAY);
                tableDetalle.addCell(cellCliente);
                // CELDA DE LA COLUMNA CINRO ---
                PdfPCell cellCinro = new PdfPCell(new Phrase(cinro, FontFactory.getFont("arial", 10, Font.NORMAL)));
                cellCinro.setUseAscender(true); // HABILITO EL AJUSTE DE LA ALTURA DE LA CELDA 
                cellCinro.setVerticalAlignment(Element.ALIGN_MIDDLE); // ALINEACION VERTICAL EN LA CELDA 
                cellCinro.setHorizontalAlignment(Element.ALIGN_CENTER); // ALINEACION HORIZONTAL EN LA CELDA 
                cellCinro.setPadding(5f); // LE AÑADO UN PADDING A LA CELDA Y AUTOMATICAMENTE AÑADE A LAS OTRAS CELDAS DE LAS OTRAS COLUMNAS TAMBIEN, EN CASO DE QUE NO LO HICIERA ENTONCES CADA INSTANCIA DE CADA CELDA TENDRA QUE TENER ESTA LINEA DE CODIGO 
                cellCinro.setBorder(Rectangle.BOTTOM);
                cellCinro.setBorderColor(BaseColor.GRAY);
                tableDetalle.addCell(cellCinro);
                // CELDA DE LA COLUMNA DIRECCION ---
                PdfPCell cellDireccion = new PdfPCell(new Phrase(direccion, FontFactory.getFont("arial", 9, Font.NORMAL)));
                cellDireccion.setUseAscender(true); // HABILITO EL AJUSTE DE LA ALTURA DE LA CELDA 
                cellDireccion.setVerticalAlignment(Element.ALIGN_MIDDLE); // ALINEACION VERTICAL EN LA CELDA 
                cellDireccion.setHorizontalAlignment(Element.ALIGN_LEFT); // ALINEACION HORIZONTAL EN LA CELDA 
                cellDireccion.setPadding(5f); // LE AÑADO UN PADDING A LA CELDA Y AUTOMATICAMENTE AÑADE A LAS OTRAS CELDAS DE LAS OTRAS COLUMNAS TAMBIEN, EN CASO DE QUE NO LO HICIERA ENTONCES CADA INSTANCIA DE CADA CELDA TENDRA QUE TENER ESTA LINEA DE CODIGO 
                cellDireccion.setBorder(Rectangle.BOTTOM);
                cellDireccion.setBorderColor(BaseColor.GRAY);
                tableDetalle.addCell(cellDireccion);
                // CELDA DE LA COLUMNA TELEFONO ---
                PdfPCell cellTelefono = new PdfPCell(new Phrase(telefono, FontFactory.getFont("arial", 10, Font.NORMAL)));
                cellTelefono.setUseAscender(true); // HABILITO EL AJUSTE DE LA ALTURA DE LA CELDA 
                cellTelefono.setVerticalAlignment(Element.ALIGN_MIDDLE); // ALINEACION VERTICAL EN LA CELDA 
                cellTelefono.setHorizontalAlignment(Element.ALIGN_LEFT); // ALINEACION HORIZONTAL EN LA CELDA 
                cellTelefono.setPadding(5f); // LE AÑADO UN PADDING A LA CELDA Y AUTOMATICAMENTE AÑADE A LAS OTRAS CELDAS DE LAS OTRAS COLUMNAS TAMBIEN, EN CASO DE QUE NO LO HICIERA ENTONCES CADA INSTANCIA DE CADA CELDA TENDRA QUE TENER ESTA LINEA DE CODIGO 
                cellTelefono.setBorder(Rectangle.BOTTOM);
                cellTelefono.setBorderColor(BaseColor.GRAY);
                tableDetalle.addCell(cellTelefono);
            }
            // LA GRILLA SE PEGA MUCHO POR LA ULTIMA LINEA ENTONCES LE DOY MAS ESPACIO PARA QUE NO SE JUNTE MUCHO 
            tableDetalle.setSpacingBefore(15);
            documento.add(tableDetalle);
            
            // AÑADO UNA LINEA EN BLANCO Y CIERRO EL DOCUMENTO 
            documento.add(Chunk.NEWLINE);
            documento.close();
            // -----------------------------------------------------------------------------------------------------
            // -----------------------------------------------------------------------------------------------------
            
            // Hay que configurar las cabeceras para que el navegador detecte que es un PDF
            response.setHeader("Expires", "0");
            response.setHeader("Cache - Control", "must - revalidate, post - check = 0, pre - check = 0");
            response.setHeader("Pragma", "public  ");
            // Configuramos el content type
            response.setContentType ("application/pdf");
            // Tamaño
            response.setContentLength (baos.size());
            // Esccribir el ByteArrayOutputStream a el ServletOutputStream
            OutputStream os = response.getOutputStream();
            baos.writeTo (os);
            os.flush ();
            os.close ();
        } catch(DocumentException e) {
            throw new IOException(e.getMessage());
        }
        
    } // END doPost() 
    
} // END CLASS 