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
@WebServlet(name="pdf_paciente_afp", urlPatterns="/pdf_afp")
public class CrearPdfPacienteVerExpAFP extends HttpServlet {
    
    
    
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
            documento.setMargins(0, 0, 20, 20); // IZQUIERDA, DERECHA, ARRIBA, ABAJO 
            // paso 2: creamos un ByteArrayOutputStream 
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
                
                // RECUPERO EL IDPACIENTE PARA OBTENER LOS DATOS DEL PACIENTE 
                String idPaciente = (String) sesionDatosUsuario.getAttribute("ID_PACIENTE");
                listaDatos = metodosPersona.traerDatosPersona(idPaciente);
                Iterator<BeanPersona> iterPaciente = listaDatos.iterator();
                BeanPersona paciente = null;
                String PACIENTE_NAME="";
                while(iterPaciente.hasNext()) {
                    paciente = iterPaciente.next();
                    PACIENTE_NAME = paciente.getRP_NOMBRE()+" "+paciente.getRP_APELLIDO();
//                    PAC_CINRO = paciente.getRP_CINRO();
                }
                
                // RECUPERO EL CAMPO DE TEXTO DE ANTECEDENTES FAMILIARES Y PERSONALES 
                String txt_antecedentes_flia = (String) request.getParameter("tAFP");
                System.out.println("_   _PDF___TXT_ANTECEDENTES_FAMILIARES_Y_PERSONALES:   :"+txt_antecedentes_flia);
                
                // INSTANCIO VALORES COMUNES ---------- 
                float valor5f = 5f;
                float valor10f = 10f;
                float valor15f = 15f;
                float valor20f = 20f;
                
                // CARGO EL TITULO DE LA PAGINA ------- 
                documento.addTitle("Antecedentes Familiares y Personales");
                
                // CARGO EL TITULO DE LA HOJA --------- 
                String empresa = metodosIniSes.devolverEmpresa();
                Paragraph titulo = new Paragraph(empresa, FontFactory.getFont("arial", 16, Font.BOLD));
                titulo.setAlignment(1); // ALINEO EL TITULO // center 
                titulo.setSpacingAfter(valor10f); // ESPACIO VERTICAL DEL TITULO CON EL PROXIMO ELEMENTO 
                documento.add(titulo);
//                documento.add(Chunk.NEWLINE); // LINEA EN BLANCO // ENTER 
                // CARGO EL SUBTITULO ----------------- 
                Paragraph subtitulo = new Paragraph("Antecedentes Familiares y Personales", FontFactory.getFont("arial", 15, Font.BOLD));
                subtitulo.setAlignment(1);
                subtitulo.setSpacingAfter(valor10f);
                documento.add(subtitulo);
                
            // DETALLE -------------- ----------------- ---------------- -------------- ----------------- ----------------
                
                // INSTANCIO LA TABLA CARGANDOLA CON DOS COLUMNAS 
                PdfPTable tablaDetalle = new PdfPTable(2);
                // LE CARGO LOS BORDES CERO PARA LUEGO CARGARLES BORDES INFERIORES ------- 
                tablaDetalle.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                // LE DEFINO EL ANCHO TOTAL DE LA TABLA 
                tablaDetalle.setTotalWidth(400);
                // CREO UN ARREGLO QUE CONTIENE LAS MEDIDAS DE CADA UNA DE LAS COLUMNAS --------- 
                float[] medidaCeldaDet = {150, 250};
                // ASIGNO LA POSICION DE LA TABLA (ANCHO) ---------- 
                tablaDetalle.setWidths(medidaCeldaDet);
                // ASIGNO LA POSICION DE LA TABLA ------------- 
                tablaDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
                // CARGO LAS COLUMNAS DE LA TABLA ------------- 
                // LABEL, PACIENTE 
                PdfPCell lblPaciente = new PdfPCell(new Phrase("Paciente: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblPaciente.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblPaciente.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblPaciente.setPaddingTop(valor10f);
                lblPaciente.setPaddingRight(valor20f);
                lblPaciente.setBorder(Rectangle.NO_BORDER);
                tablaDetalle.addCell(lblPaciente);
                // VALOR DEL DATO, PACIENTE 
                PdfPCell valuePaciente = new PdfPCell(new Phrase(PACIENTE_NAME));
                valuePaciente.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valuePaciente.setPaddingTop(valor10f);
                valuePaciente.setBorder(Rectangle.NO_BORDER);
                tablaDetalle.addCell(valuePaciente);
                // LABEL, ANTECEDENTES FAMILIARES Y PERSONALES 
                PdfPCell lblAFP = new PdfPCell(new Phrase("Antecedentes: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblAFP.setVerticalAlignment(Element.ALIGN_MIDDLE); // centrado vertical 
                lblAFP.setHorizontalAlignment(Element.ALIGN_RIGHT); // centrado horizontal 
                lblAFP.setPaddingTop(valor10f);
                lblAFP.setPaddingRight(valor20f);
                lblAFP.setBorder(Rectangle.NO_BORDER);
                tablaDetalle.addCell(lblAFP); // AÑADO A LA TABLA LA CELDA 
                // VALOR DEL DATO, ANTECEDENTES FAMILIARES Y PERSONALES 
                PdfPCell valueAFP = new PdfPCell(new Phrase(txt_antecedentes_flia));
                valueAFP.setVerticalAlignment(Element.ALIGN_MIDDLE); // centrado vertical 
                valueAFP.setPaddingTop(valor10f);
                valueAFP.setBorder(Rectangle.NO_BORDER);
                tablaDetalle.addCell(valueAFP); // AÑADO A LA TABLA DE CELDA 
                
                // LA GRILLA SE PEGA MUCHO POR LA ULTIMA LINEA ENTONCES LE DOY MAS ESPACIO PARA QUE NO SE JUNTE MUCHO 
                tablaDetalle.setSpacingBefore(valor15f);
                documento.add(tablaDetalle);
                
                // AÑADO UNA LINEA EN BLANCO Y CIERRO EL DOCUMENTO 
                documento.add(Chunk.NEWLINE);
                documento.close();
            // -----------------------------------------------------------------------------------------------------
            // -----------------------------------------------------------------------------------------------------
            
            // Hay que configurar las cabeceras para que el navegador detecte que es un PDF 
            response.setHeader("Expires", "0");
            response.setHeader("Cache - Control", "must - revalidate, post - check = 0, pre - check = 0");
            response.setHeader("Pragma", "public ");
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