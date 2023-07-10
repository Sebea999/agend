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
import com.agend.javaBean.BeanAgendamiento;
import com.agend.javaBean.BeanPersona;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.agend.modelo.ModelAgendamiento;
import com.agend.modelo.ModelInicioSesion;
import com.agend.modelo.ModelPersona;
import com.agend.modelo.ModelRef_Clinica;
import com.agend.modelo.ModelRef_Especialidad;

/**
 *
 * @author RYUU
 */
@WebServlet(name="crearPdfMisAgend", urlPatterns="/mi_agend_pdf")
public class CrearPdfRptMisAgend extends HttpServlet {
    
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
        doPost(request, response);
    }
    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // INSTANCIO UN DOCUMENTO DONDE CREARE EL PDF 
            Document documento = new Document(PageSize.LEGAL);
            // LE DEFINO LOS MARGENES A LA HOJA CON CERO A LOS COSTADOS PARA DARLE MAS ESPACIO A LA TABLA 
            documento.setMargins(0, 0, 20, 20); // IZQUIERDA - DERECHA - ARRIBA - ABAJO 
            // PASO 2: Creamos un ByteArrayOutputStream, todo lo que se escriba en el documento, se escribe tambien en el ByteArrayOutputStream 
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
//                    PdfWriter.getInstance(documento, new FileOutputStream("C:\\Empeno\\prueba_plantilla.pdf"));
//                } catch (FileNotFoundException fileNotFoundException) { // NORMALMENTE DARIA ESTE ERROR CUANDO EL ARCHIVO ESTE ABIERTO O LO ESTE USANDO OTRO PROCESO // SI YA ESTA CREADO PERO NO ESTA SIENDO UTILIZADO NO VA A DAR ERROR, VA A SOBREESCRIBIR 
//                    System.out.println("No such file was found to generate the PDF "
//                            + "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
//                }
                // ABRO EL DOCUMENTO PARA PODER IR CARGANDO CON EL CONTENIDO 
                documento.open();
                
                // INSTANCIO VARIABLES QUE UTILIZARE COMO UNIDADES DE MEDIDA Y ESTANDARIZAR ASI 
                float value5f = 5f;
                float value10f = 10f;
                float value15f = 15f;
                float value20f = 20f;
                
                // INSTANCIO LA SESION DONDE PUEDO RECUPERAR DATOS DEL USUARIO 
                HttpSession sesionDatosUsuario = request.getSession();
                // COMO RECUPERO EL IDCLIENTE, ENTONCES LLAMO A OTRO METODO PARA RECUPERAR ALGUNOS DATOS DEL CLIENTE PARA PODER MOSTRAR 
                ModelInicioSesion metodosIniSes = new ModelInicioSesion();
                ModelPersona metodosPersona = new ModelPersona();
                ModelAgendamiento metodosAgendamiento = new ModelAgendamiento();
                List<BeanAgendamiento> listaDatos = new ArrayList<>();
                BeanAgendamiento datosAgen = new BeanAgendamiento();
                BeanPersona datosPaciente = new BeanPersona();
                ModelRef_Especialidad metodosRefEspecialidad = new ModelRef_Especialidad();
                ModelRef_Clinica metodosRefClinica = new ModelRef_Clinica();
                
                // PODRIA UTILIZAR LA SESION PERO COMO ESTE SERVLET PARA IMPRIMIR EMPEÑO UTILIZO TAMBIEN EN REPORTES Y AHI DEBE DE SELECCIONAR DE UNA GRILLA SI QUIERE IMPRIMIR, ENTONCES POR ESO PREGUNTO POR EL VALOR DEL CAMPO DE TEXTO, SI ESTA NULO ES PORQUE SE CARGO EL IDAGENDAMIENTO POR MEDIO DE LA SESION Y NO ESTA NULO ES PORQUE NO SE CARGA POR LA SESION Y POR ESO CARGO EL IDEMPENHO EN UN CAMPO DE TEXTO 
                String idAgendamiento, itemAgen;
                if (String.valueOf(request.getParameter("tI")) == null || String.valueOf(request.getParameter("tI")).isEmpty()) {
                    idAgendamiento = "";
                    itemAgen = "";
                } else {
                    idAgendamiento = (String) request.getParameter("tI");
                    itemAgen = (String) request.getParameter("tIt");
                }
                
                // CARGO EL TITULO DE LA PAGINA ---------- 
                documento.addTitle("Detalle del Agendamiento");
                // CARGO EL TITULO DE LA HOJA ------------ 
                String empresa = metodosIniSes.devolverEmpresa();
                Paragraph titulo = new Paragraph(empresa, FontFactory.getFont("arial", 16, Font.BOLD));
                titulo.setAlignment(1); // ALINEO EL TITULO // CENTER 
                titulo.setSpacingAfter(10f); // ESPACIO VERTICAL DEL TITULO CON EL PROXIMO ELEMENTO 
                documento.add(titulo);
//                documento.add(Chunk.NEWLINE); // LINEA EN BLANCO // ENTER 
                
                // CARGO EL SUBTITULO -------------------- 
                Paragraph subtitulo = new Paragraph("Detalle del Agendamiento", FontFactory.getFont("arial", 15, Font.BOLD));
                subtitulo.setAlignment(1); // ALINEO EL TITULO // CENTER 
                subtitulo.setSpacingAfter(10f); // ESPACIO VERTICAL DEL TITULO CON EL PROXIMO ELEMENTO 
                documento.add(subtitulo);
                
                
            // DETALLE -------------- ----------------- ---------------- -------------- ----------------- ----------------
                listaDatos = metodosAgendamiento.traer_datos_det(idAgendamiento, itemAgen, sesionDatosUsuario);
                Iterator<BeanAgendamiento> iterAgen = listaDatos.iterator();
                String txtAgenIdClinica="", txtAgenClinica="", txtAgenIdEspecialidad="", txtAgenEspecialidad="", txtAgenIdMedico="", txtAgenMedico="", txtAgenNroCiMed="", txtAgenFechaHora="", txtAgenIdPaciente="", txtAgenPaciente="", txtAgenCinroPaciente="";
                while(iterAgen.hasNext()) {
                    datosAgen = iterAgen.next();
                    
                    txtAgenIdClinica = datosAgen.getOA_IDCLINICA();
                    txtAgenIdEspecialidad = datosAgen.getOA_IDESPECIALIDAD();
                    txtAgenIdMedico = datosAgen.getOA_IDMEDICO();
                    txtAgenFechaHora = datosAgen.getOAD_FECHA_AGEN()+" "+datosAgen.getOAD_HORA();
                    txtAgenIdPaciente = datosAgen.getOAD_IDPACIENTE();
                }
                
                // RECUPERO LOS DATOS DE LAS DESCRIPCIONES A TRAVES DE LOS ID QUE RECUPERO 
                BeanPersona datosMed = new BeanPersona();
                datosMed = metodosPersona.datosBasicosPersona(datosMed, txtAgenIdMedico);
                txtAgenMedico = datosMed.getRP_NOMBRE()+" "+datosMed.getRP_APELLIDO();
                txtAgenNroCiMed = datosMed.getRP_CINRO();
                datosPaciente = metodosPersona.datosBasicosPersona(datosPaciente, txtAgenIdPaciente);
                txtAgenPaciente = datosPaciente.getRP_NOMBRE()+" "+datosPaciente.getRP_APELLIDO();
                txtAgenCinroPaciente = datosPaciente.getRP_CINRO();
                txtAgenEspecialidad = metodosRefEspecialidad.traerDescEspecialidad(txtAgenIdEspecialidad); // DESCRIPCION DE LA ESPECIALIDAD 
                txtAgenClinica = metodosRefClinica.traerDescClinica(txtAgenIdClinica);
                
                // INSTANCIO LA TABLA CARGANDOLA CON DOS COLUMNAS 
                PdfPTable tableDetalle = new PdfPTable(2);
                // LE CARGO LOS BORDES CERO PARA LUEGO CARGARLE BORDES INFERIORES NOMAS ---------- ------------ 
                tableDetalle.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                // LE DEFINO EL ANCHO TOTAL DE LA TABLA 
                tableDetalle.setTotalWidth(400);
                // CREO UN ARREGLO QUE CONTIENE LAS MEDIDAS DE CADA UNA DE LAS COLUMNSA ---------- ------------- 
                float[] medidaCeldaDet = {150, 250};
                // ASIGNAS LAS MEDIDAS A LA TABLA (ANCHO) ------------ ------------- 
                tableDetalle.setWidths(medidaCeldaDet);
                // LE ASIGNO LA POSICION DE LA TABLA ------------ ------------- 
                tableDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
                // CARGO LAS COLUMNAS DE LA TABLA  ------------ ------------- 
                // LABEL, IDAGENDAMIENTO 
                PdfPCell lblIdAgen = new PdfPCell(new Phrase("Código: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblIdAgen.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                lblIdAgen.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
                lblIdAgen.setPaddingTop(value10f);
                lblIdAgen.setPaddingRight(value20f);
                lblIdAgen.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(lblIdAgen); // AÑADO A LA TABLA LA CELDA 
                // LINEA DEL DATO, IDAGENDAMIENTO 
                PdfPCell valueIdAgen = new PdfPCell(new Phrase(idAgendamiento, FontFactory.getFont("arial", 12, Font.BOLD)));
                valueIdAgen.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                valueIdAgen.setPaddingTop(value10f);
                valueIdAgen.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(valueIdAgen); // AÑADO A LA TABLA LA CELDA 
                
                // LABEL, CLINICA 
                PdfPCell lblClinica = new PdfPCell(new Phrase("Clínica: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblClinica.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                lblClinica.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
                lblClinica.setPaddingTop(value10f);
                lblClinica.setPaddingRight(value20f);
                lblClinica.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(lblClinica); // AÑADO A LA TABLA LA CELDA 
                // LINEA DEL DATO, CLINICA 
                PdfPCell valueClinica = new PdfPCell(new Phrase(txtAgenClinica, FontFactory.getFont("arial", 12, Font.NORMAL)));
                valueClinica.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                valueClinica.setPaddingTop(value10f);
                valueClinica.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(valueClinica); // AÑADO A LA TABLA LA CELDA 
                
                // LABEL, ESPECIALIDAD 
                PdfPCell lblEspecialidad = new PdfPCell(new Phrase("Especialidad: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblEspecialidad.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                lblEspecialidad.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
                lblEspecialidad.setPaddingTop(value10f);
                lblEspecialidad.setPaddingRight(value20f);
                lblEspecialidad.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(lblEspecialidad); // AÑADO A LA TABLA LA CELDA 
                // LINEA DEL DATO, ESPECIALIDAD 
                PdfPCell valueEspecialidad = new PdfPCell(new Phrase(txtAgenEspecialidad, FontFactory.getFont("arial", 12, Font.NORMAL)));
                valueEspecialidad.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                valueEspecialidad.setPaddingTop(value10f);
                valueEspecialidad.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(valueEspecialidad); // AÑADO A LA TABLA LA CELDA 
                
                // LABEL, MEDICO  
                PdfPCell lblMedico = new PdfPCell(new Phrase("Médico: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblMedico.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                lblMedico.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
                lblMedico.setPaddingTop(value10f);
                lblMedico.setPaddingRight(value20f);
                lblMedico.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(lblMedico); // AÑADO A LA TABLA LA CELDA 
                // LINEA DEL DATO, MEDICO 
                PdfPCell valueMedico = new PdfPCell(new Phrase(txtAgenMedico, FontFactory.getFont("arial", 12, Font.NORMAL)));
                valueMedico.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                valueMedico.setPaddingTop(value10f);
                valueMedico.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(valueMedico); // AÑADO A LA TABLA LA CELDA 
                
                // LABEL, FECHA_HORA_AGEN 
                PdfPCell lblFechaHoraAgen = new PdfPCell(new Phrase("Fecha y Hora de Agen.: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblFechaHoraAgen.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                lblFechaHoraAgen.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
                lblFechaHoraAgen.setPaddingTop(value10f);
                lblFechaHoraAgen.setPaddingRight(value20f);
                lblFechaHoraAgen.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(lblFechaHoraAgen); // AÑADO A LA TABLA LA CELDA 
                // LINEA DEL DATO, FECHA_HORA_AGEN 
                PdfPCell valueFechaHoraAgen = new PdfPCell(new Phrase(txtAgenFechaHora, FontFactory.getFont("arial", 12, Font.NORMAL)));
                valueFechaHoraAgen.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                valueFechaHoraAgen.setPaddingTop(value10f);
                valueFechaHoraAgen.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(valueFechaHoraAgen); // AÑADO A LA TABLA LA CELDA 
                
                // LABEL, PACIENTE 
                PdfPCell lblPaciente = new PdfPCell(new Phrase("Paciente: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblPaciente.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                lblPaciente.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
                lblPaciente.setPaddingTop(value10f);
                lblPaciente.setPaddingRight(value20f);
                lblPaciente.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(lblPaciente); // AÑADO A LA TABLA LA CELDA 
                // LINEA DEL DATO, PACIENTE 
                PdfPCell valuePaciente = new PdfPCell(new Phrase((txtAgenCinroPaciente+" - "+txtAgenPaciente), FontFactory.getFont("arial", 12, Font.NORMAL)));
                valuePaciente.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                valuePaciente.setPaddingTop(value10f);
                valuePaciente.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(valuePaciente); // AÑADO A LA TABLA LA CELDA 
                
                // LA GRILLA SE PEGA MUCHO POR LA ULTIMA LINEA ENTONCES LE DOY MAS ESPACIO PARA QUE NO SE JUNTE MUCHO 
                tableDetalle.setSpacingBefore(value15f);
                documento.add(tableDetalle);
                
                // AÑADO UNA LINEA EN BLANCO Y CIERRO EL DOCUMENTO 
                documento.add(Chunk.NEWLINE);
                documento.close();
            // -----------------------------------------------------------------------------------------------------
            // -----------------------------------------------------------------------------------------------------
            
            // Hay que configurar la cabedera para que el navegador detecte que es un PDF 
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
        } catch(DocumentException e) {
            throw new IOException(e.getMessage());
        }
    } // END DO_POST
    
} // END CLASS 