/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.modelo.pdf;

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
import java.util.ArrayList;
import java.util.Iterator;
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
@WebServlet(name="pdf_atencion_tratamiento", urlPatterns="/tratamiento_pdf")
public class PdfAtencion_Tratamiento extends HttpServlet {
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        doPost(request, response);
    }
    
    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        imprimir_pdf(request, response);
    } // END DO_POST 
    
    
    
    public void imprimir_pdf(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        try {
            // INSTANCIO UN DOCUMENTO DONDE CREARE EL PDF 
            Document documento = new Document(PageSize.LEGAL);
            // LE DEFINO LOS MARGENES A LA HOJA CON CERO A LOS COSTADOS PARA DARLE MAS ESPACIO A LA TABLA 
            documento.setMargins(0, 0, 20, 20); // IZQUIERDA - DERECHA - ARRIBA - ABAJO 
            // Paso 2: Creamos un ByteArrayOutputStream 
            // todo lo que se escriba en el documento se escribe tambien en el ByteArrayOutputStream 
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(documento, baos);
            
            // -----------------------------------------------------------------------------------------------------
            //              CODIGO PARA CREAR EL PDF 
            // -----------------------------------------------------------------------------------------------------
            System.out.println(".");
            System.out.println("..");
            System.out.println("--------------------    CREAR_PDF_SERVLET()    --------------------");
            System.out.println("..");
            System.out.println(".");
            // FRAGMENTO DE CODIGO PARA GUARDAR EL ARCHIVO PDF 
//                try {
//                    PdfWriter.getInstance(documento, new FileOutputStream("C:\\Atencion\\prueba_plantilla.pdf"));
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
                List<BeanPersona> listaDatos = new ArrayList<>();
                BeanPersona datosPaciente = new BeanPersona();
                
                // RECUPERO EL IDPACIENTE PARA OBTENER LOS DATOS DEL PACIENTE 
                String idPaciente = (String) sesionDatosUsuario.getAttribute("CFAP_IDPACIENTE");
                // RECUPERO EL CAMPO DE TEXTO DE TRATAMIENTO 
                String txt_tratamiento = (String) request.getParameter("tAT");
                System.out.println("_   _PDF___TRATAMIENTO_TXT:     :"+txt_tratamiento);
                
                // INSTANCIO VALORES COMUNES ---------- 
                float valor5f = 5f;
                float valor10f = 10f;
                float valor15f = 15f;
                float valor20f = 20f;
                
                // CARGO EL TITULO DE LA PAGINA ------- 
                documento.addTitle("Detalle del Tratamiento");
                
                // CARGO EL TITULO DE LA HOJA --------- 
                String empresa = metodosIniSes.devolverEmpresa();
                Paragraph titulo = new Paragraph(empresa, FontFactory.getFont("arial", 16, Font.BOLD));
                titulo.setAlignment(1); // ALINEO EL TITULO // center 
                titulo.setSpacingAfter(valor10f); // ESPACIO VERTICAL DEL TITULO CON EL PROXIMO ELEMENTO 
                documento.add(titulo);
//                documento.add(Chunk.NEWLINE); // LINEA EN BLANCO // ENTER 
                // CARGO EL SUBTITULO ---------------- 
                Paragraph subtitulo = new Paragraph("Detalles del Tratamiento", FontFactory.getFont("arial", 15, Font.BOLD));
                subtitulo.setAlignment(1);
                subtitulo.setSpacingAfter(valor10f);
                documento.add(subtitulo);
                
            // DETALLE -------------- ----------------- ---------------- -------------- ----------------- ----------------
                listaDatos = metodosPersona.traerDatosPersona(idPaciente);
                Iterator<BeanPersona> iterPaciente = listaDatos.iterator();
                String pacCinro="", pacApellido="", pacNombre="", pacDireccion="", pacNroTelf="";
                while(iterPaciente.hasNext()) {
                    datosPaciente = iterPaciente.next();
                    
                    pacCinro = datosPaciente.getRP_CINRO();
                    pacApellido = datosPaciente.getRP_APELLIDO();
                    pacNombre = datosPaciente.getRP_NOMBRE();
                    pacDireccion = datosPaciente.getRP_DIRECCION();
                    pacNroTelf = datosPaciente.getRP_TELFMOVIL();
                }
                
                // INSTANCIO LA TABLA CARGANDOLA CON DOS COLUMNAS 
                PdfPTable tableDetalle = new PdfPTable(2);
                // LE CARGO LOS BORDES CERO PARA LUEGO CARGARLES BORDES INFERIORES ---------
                tableDetalle.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                // LE DEFINO EL ANCHO TOTAL DE LA TABLA 
                tableDetalle.setTotalWidth(400);
                // CREO UN ARREGLO QUE CONTIENE LAS MEDIDAS DE CADA UNA DE LAS COLUMNAS ---------- 
                float[] medidaCeldaDet = {150, 250};
                // ASIGNO LAS MEDIDAS DE LA TABLA (ANCHO) ---------- 
                tableDetalle.setWidths(medidaCeldaDet);
                // ASIGNO LA POSICION DE LA TABLA ----------- 
                tableDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
                // CARGO LAS COLUMNAS DE LA TABLA ----------- 
//                // LABEL, ID_PACIENTE 
//                PdfPCell lblIdPaciente = new PdfPCell(new Phrase("Código: ", FontFactory.getFont("arial", 12, Font.BOLD)));
//                lblIdPaciente.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
//                lblIdPaciente.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
//                lblIdPaciente.setPaddingTop(valor10f);
//                lblIdPaciente.setPaddingRight(valor20f);
//                lblIdPaciente.setBorder(Rectangle.NO_BORDER);
//                tableDetalle.addCell(lblIdPaciente); // AÑADO A LA TABLA LA CELDA 
//                // VALOR DEL DATO, ID_PACIENTE 
//                PdfPCell valueIdPaciente = new PdfPCell(new Phrase(idPaciente, FontFactory.getFont("arial", 12, Font.NORMAL)));
//                valueIdPaciente.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
//                valueIdPaciente.setPaddingTop(valor10f);
//                valueIdPaciente.setBorder(Rectangle.NO_BORDER);
//                tableDetalle.addCell(valueIdPaciente); // AÑADO A LA TABLA LA CELDA 
                
                // LABEL, NOMBRE Y APELLIDO DEL PACIENTE 
                PdfPCell lblPacNomApe = new PdfPCell(new Phrase("Nombre/s y Apellido/s: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblPacNomApe.setVerticalAlignment(Element.ALIGN_MIDDLE); // centrado vertical 
                lblPacNomApe.setHorizontalAlignment(Element.ALIGN_RIGHT); // centrado horizontal 
                lblPacNomApe.setPaddingTop(valor10f);
                lblPacNomApe.setPaddingRight(valor20f);
                lblPacNomApe.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(lblPacNomApe); // AÑADO A LA TABLA LA CELDA 
                // VALOR DEL DATO, NOMBRE Y APELLIDO DEL PACIENTE 
                PdfPCell valuePacNomApe = new PdfPCell(new Phrase(pacNombre+" "+pacApellido));
                valuePacNomApe.setVerticalAlignment(Element.ALIGN_MIDDLE); // centrado vertical 
                valuePacNomApe.setPaddingTop(valor10f);
                valuePacNomApe.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(valuePacNomApe); // AÑADO A LA TABLA LA CELDA 
                
                // LABEL, TRATAMIENTO 
                PdfPCell lblTratamiento = new PdfPCell(new Phrase("Tratamiento: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblTratamiento.setVerticalAlignment(Element.ALIGN_TOP); // centrado vertical 
                lblTratamiento.setHorizontalAlignment(Element.ALIGN_RIGHT); // centrado horizontal 
                lblTratamiento.setPaddingTop(valor10f);
                lblTratamiento.setPaddingRight(valor20f);
                lblTratamiento.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(lblTratamiento); // AÑADO A LA TABLA LA CELDA 
                // VALOR DEL DATO, TRATAMIENTO 
                PdfPCell valueTratamiento = new PdfPCell(new Phrase(txt_tratamiento));
                valueTratamiento.setVerticalAlignment(Element.ALIGN_MIDDLE); // centrado vertical 
                valueTratamiento.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                valueTratamiento.setPaddingTop(valor10f);
                valueTratamiento.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(valueTratamiento); // AÑADO A LA TABLA LA CELDA 
                
                // LA GRILLA SE PEGA MUCHO POR LA ULTIMA LINEA ENTONCES LE DOY MAS ESPACIO PARA QUE NO SE JUNTE MUCHO 
                tableDetalle.setSpacingBefore(valor15f);
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
            response.setContentType("application/pdf");
            // tamaño 
            response.setContentLength(baos.size());
            // escribir el ByteArrayOutputStream a el ServletOutputStream 
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
    }
    
    
} // END CLASS 