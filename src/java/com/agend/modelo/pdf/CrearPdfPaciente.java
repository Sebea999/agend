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
@WebServlet(name="crearPdfPaciente", urlPatterns="/paciente_pdf")
public class CrearPdfPaciente extends HttpServlet {
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
        doPost(request, response);
    }
    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // INSTANCIO UN DOCUMENTO DONDE CREARE EL PDF 
            Document documento = new Document(PageSize.LEGAL);
//            Document documento = new Document();
            // LE DEFINO LOS MARGENES A LA HOJA CON CERO A LOS COSTADOS PARA DARLE MAS ESPACIO A LA TABLA 
            documento.setMargins(0, 0, 20, 20); // IZQUIERDA - DERECHA - ARRIBA - ABAJO 
            // Paso 2: Creamos un ByteArrayOutputStream
            // todo lo que se escriba en el documento
            // se escribe tambien en el ByteArrayOutputStream
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
                
                // INSTANCIO LA SESION DONDE PUEDO RECUPERAR DATOS DEL USUARIO 
                HttpSession sesionDatosUsuario = request.getSession();
                // COMO RECUPERO EL IDCLIENTE, ENTONCES LLAMO A OTRO METODO PARA RECUPERAR ALGUNOS DATOS DEL CLIENTE PARA PODER MOSTRAR  
                ModelInicioSesion metodosIniSes = new ModelInicioSesion();
                ModelPersona metodosPersona = new ModelPersona();
                List<BeanPersona> listaDatos = new ArrayList<>();
                BeanPersona datosCliente = new BeanPersona();
                
                // PODRIA UTILIZAR LA SESION PERO COMO ESTE SERVLET PARA IMPRIMIR EMPEÑO UTILIZO TAMBIEN EN REPORTES Y AHI DEBE DE SELECCIONAR DE UNA GRILLA SI QUIERE IMPRIMIR, ENTONCES POR ESO PREGUNTO POR EL VALOR DEL CAMPO DE TEXTO, SI ESTA NULO ES PORQUE SE CARGO EL IDEMPENHO POR MEDIO DE LA SESION Y SI NO ESTA NULO ES PORQUE NO SE CARGA POR LA SESION Y POR ESO CARGO EL IDEMPENHO EN UN CAMPO DE TEXTO 
                String idCliente;
                if (String.valueOf(request.getParameter("tIC")) == null || String.valueOf(request.getParameter("tIC")).isEmpty()) {
                    idCliente = (String) sesionDatosUsuario.getAttribute("ID_CLIENTE");
                } else {
                    idCliente = (String) request.getParameter("tIC");
                }
                
                // CARGO EL TITULO DE LA PAGINA ---------
                documento.addTitle("Detalle del Paciente "+idCliente+"");
                // CARGO EL TITULO DE LA HOJA -----------
                String empresa = metodosIniSes.devolverEmpresa();
                Paragraph titulo = new Paragraph(empresa, FontFactory.getFont("arial", 16, Font.BOLD));
                titulo.setAlignment(1);// ALINEO EL TITULO  // center 
                titulo.setSpacingAfter(10f);// ESPACIO VERTICAL DEL TITULO CON EL PROXIMO ELEMENTO 
                documento.add(titulo);
//                documento.add(Chunk.NEWLINE); // LINEA EN BLANCO // ENTER 
                // CARGO EL SUBTITULO -------------------
                Paragraph subtitulo = new Paragraph("Detalle del Paciente", FontFactory.getFont("arial", 15, Font.BOLD));
                subtitulo.setAlignment(1); // ALINEO EL TITULO // center 
                subtitulo.setSpacingAfter(10f);// ESPACIO VERTICAL DEL TITULO CON EL PROXIMO ELEMENTO 
                documento.add(subtitulo);

                
            // DETALLE -------------- ----------------- ---------------- -------------- ----------------- ----------------
                listaDatos = metodosPersona.traerDatosPersona(idCliente);
//                datosCliente = metodosPersona.datosBasicosPersona(datosCliente, idCliente);
                Iterator<BeanPersona> iterCliente = listaDatos.iterator();
                String txtClienteCinro="", txtClienteApellido="", txtClienteNombre="", txtClienteDireccion="", txtClienteTelefono="", txtClienteEmail="", txtClienteUsuario="", txtClientePassword="", txtCliUsuEstado="";
                while(iterCliente.hasNext()) {
                    datosCliente = iterCliente.next();
                    
                    txtClienteCinro = datosCliente.getRP_CINRO();
                    txtClienteApellido = datosCliente.getRP_APELLIDO();
                    txtClienteNombre = datosCliente.getRP_NOMBRE();
                    txtClienteTelefono = datosCliente.getRP_TELFMOVIL();
                    txtClienteDireccion = datosCliente.getRP_DIRECCION();
                    txtClienteEmail = datosCliente.getRP_EMAIL();
                    txtClienteUsuario = datosCliente.getSU_USUARIO();
                    txtClientePassword = datosCliente.getSU_CLAVE();
                    txtCliUsuEstado = datosCliente.getSU_ESTADO();
                }
                
                // INSTANCIO LA TABLA CARGANDOLA CON DOS COLUMNAS 
                PdfPTable tableDetalle = new PdfPTable(2);
                // LE CARGO LOS BORDES CERO PARA LUEGO CARGARLE BORDES INFERIORES NOMAS --------  -------- 
                tableDetalle.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                // LE DEFINO EL ANCHO TOTAL DE LA TABLA 
                tableDetalle.setTotalWidth(400);
                // CREO UN ARREGLO QUE CONTIENE LAS MEDIDAS DE CADA UNA DE LAS COLUMNAS --------  -------- 
                float[] medidaCeldaDet = {150, 250};
                // ASIGNAS LAS MEDIDAS A LA TABLA (ANCHO) --------  -------- 
                tableDetalle.setWidths(medidaCeldaDet);
                // LE ASIGNO LA POSICION DE LA TABLA  --------  -------- 
                tableDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
                // CARGO LAS COLUMNAS DE LA TABLA --------------
                float valuePaddingTop = 10f;
                // LABEL, ID_EMPENHO 
                PdfPCell lblIdCliente = new PdfPCell(new Phrase("Código: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblIdCliente.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                lblIdCliente.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
                lblIdCliente.setPaddingTop(valuePaddingTop);
                lblIdCliente.setPaddingRight(20f);
                lblIdCliente.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(lblIdCliente); // AÑADO A LA TABLA LA CELDA 
                // LINEA DEL DATO, ID_EMPENHO 
                PdfPCell valueIdCliente = new PdfPCell(new Phrase(idCliente, FontFactory.getFont("arial", 12, Font.BOLD)));
                valueIdCliente.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                valueIdCliente.setPaddingTop(valuePaddingTop);
                valueIdCliente.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(valueIdCliente); // AÑADO A LA TABLA LA CELDA 
                
                // LABEL, NOMBRE 
                PdfPCell lblClienteNombre = new PdfPCell(new Phrase("Nombres: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblClienteNombre.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                lblClienteNombre.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
                lblClienteNombre.setPaddingTop(valuePaddingTop);
                lblClienteNombre.setPaddingRight(20f);
                lblClienteNombre.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(lblClienteNombre); // AÑADO A LA TABLA LA CELDA 
                // LINEA DEL DATO, NOMBRE 
                PdfPCell valueNombre = new PdfPCell(new Phrase(txtClienteNombre));
                valueNombre.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                valueNombre.setPaddingTop(valuePaddingTop);
                valueNombre.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(valueNombre); // AÑADO A LA TABLA LA CELDA 
                
                // LABEL, APELLIDO 
                PdfPCell lblClienteApellido = new PdfPCell(new Phrase("Apellido: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblClienteApellido.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                lblClienteApellido.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
                lblClienteApellido.setPaddingTop(valuePaddingTop);
                lblClienteApellido.setPaddingRight(20f);
                lblClienteApellido.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(lblClienteApellido); // AÑADO A LA TABLA LA CELDA 
                // LINEA DEL DATO, APELLIDO 
                PdfPCell valueApellido = new PdfPCell(new Phrase(txtClienteApellido));
                valueApellido.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                valueApellido.setPaddingTop(valuePaddingTop);
                valueApellido.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(valueApellido); // AÑADO A LA TABLA LA CELDA 
                
                // LABEL, CINRO 
                PdfPCell lblClienteCinro = new PdfPCell(new Phrase("Nro. CI: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblClienteCinro.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                lblClienteCinro.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
                lblClienteCinro.setPaddingTop(valuePaddingTop);
                lblClienteCinro.setPaddingRight(20f);
                lblClienteCinro.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(lblClienteCinro); // AÑADO A LA TABLA LA CELDA 
                // LINEA DEL DATO, CINRO 
                PdfPCell valueCinro = new PdfPCell(new Phrase(txtClienteCinro));
                valueCinro.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                valueCinro.setPaddingTop(valuePaddingTop);
                valueCinro.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(valueCinro); // AÑADO A LA TABLA LA CELDA 
                
                // LABEL, TELEFONO 
                PdfPCell lblClienteTelefono = new PdfPCell(new Phrase("Teléfono: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblClienteTelefono.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                lblClienteTelefono.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
                lblClienteTelefono.setPaddingTop(valuePaddingTop);
                lblClienteTelefono.setPaddingRight(20f);
                lblClienteTelefono.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(lblClienteTelefono); // AÑADO A LA TABLA LA CELDA 
                // LINEA DEL DATO, TELEFONO 
                PdfPCell valueTelefono = new PdfPCell(new Phrase(txtClienteTelefono));
                valueTelefono.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                valueTelefono.setPaddingTop(valuePaddingTop);
                valueTelefono.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(valueTelefono); // AÑADO A LA TABLA LA CELDA 
                
                // LABEL, DIRECCION 
                PdfPCell lblClienteDireccion = new PdfPCell(new Phrase("Dirección: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblClienteDireccion.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                lblClienteDireccion.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
                lblClienteDireccion.setPaddingTop(valuePaddingTop);
                lblClienteDireccion.setPaddingRight(20f);
                lblClienteDireccion.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(lblClienteDireccion); // AÑADO A LA TABLA LA CELDA 
                // LINEA DEL DATO, DIRECCION 
                PdfPCell valueDireccion = new PdfPCell(new Phrase(txtClienteDireccion));
                valueDireccion.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                valueDireccion.setPaddingTop(valuePaddingTop);
                valueDireccion.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(valueDireccion); // AÑADO A LA TABLA LA CELDA 
                
                // LABEL, EMAIL 
                PdfPCell lblClienteEmail = new PdfPCell(new Phrase("Email: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblClienteEmail.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                lblClienteEmail.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
                lblClienteEmail.setPaddingTop(valuePaddingTop);
                lblClienteEmail.setPaddingRight(20f);
                lblClienteEmail.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(lblClienteEmail); // AÑADO A LA TABLA LA CELDA 
                // LINEA DEL DATO, EMAIL 
                PdfPCell valueEmail = new PdfPCell(new Phrase(txtClienteEmail));
                valueEmail.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                valueEmail.setPaddingTop(valuePaddingTop);
                valueEmail.setBorder(Rectangle.NO_BORDER);
                tableDetalle.addCell(valueEmail); // AÑADO A LA TABLA LA CELDA 
                
//                // LABEL, USUARIO 
//                PdfPCell lblClienteUsuario = new PdfPCell(new Phrase("Usuario: ", FontFactory.getFont("arial", 12, Font.BOLD)));
//                lblClienteUsuario.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
//                lblClienteUsuario.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
//                lblClienteUsuario.setPaddingTop(valuePaddingTop);
//                lblClienteUsuario.setPaddingRight(20f);
//                lblClienteUsuario.setBorder(Rectangle.NO_BORDER);
//                tableDetalle.addCell(lblClienteUsuario); // AÑADO A LA TABLA LA CELDA 
//                // LINEA DEL DATO, USUARIO 
//                PdfPCell valueUsuario = new PdfPCell(new Phrase(txtClienteUsuario));
//                valueUsuario.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
//                valueUsuario.setPaddingTop(valuePaddingTop);
//                valueUsuario.setBorder(Rectangle.NO_BORDER);
//                tableDetalle.addCell(valueUsuario); // AÑADO A LA TABLA LA CELDA 
//                
//                // LABEL, CLAVE 
//                PdfPCell lblClientePassword = new PdfPCell(new Phrase("Clave: ", FontFactory.getFont("arial", 12, Font.BOLD)));
//                lblClientePassword.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
//                lblClientePassword.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
//                lblClientePassword.setPaddingTop(valuePaddingTop);
//                lblClientePassword.setPaddingRight(20f);
//                lblClientePassword.setBorder(Rectangle.NO_BORDER);
//                tableDetalle.addCell(lblClientePassword); // AÑADO A LA TABLA LA CELDA 
//                // LINEA DEL DATO, CLAVE 
//                PdfPCell valueClave = new PdfPCell(new Phrase(txtClientePassword));
//                valueClave.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
//                valueClave.setPaddingTop(valuePaddingTop);
//                valueClave.setBorder(Rectangle.NO_BORDER);
//                tableDetalle.addCell(valueClave); // AÑADO A LA TABLA LA CELDA 
//                
//                // LABEL, ESTADO 
//                PdfPCell lblCliUsuEstado = new PdfPCell(new Phrase("Estado: ", FontFactory.getFont("arial", 12, Font.BOLD)));
//                lblCliUsuEstado.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
//                lblCliUsuEstado.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
//                lblCliUsuEstado.setPaddingTop(valuePaddingTop);
//                lblCliUsuEstado.setPaddingRight(20f);
//                lblCliUsuEstado.setBorder(Rectangle.NO_BORDER);
//                tableDetalle.addCell(lblCliUsuEstado); // AÑADO A LA TABLA LA CELDA 
//                // LINEA DEL DATO, ESTADO 
//                PdfPCell valueEstado = new PdfPCell(new Phrase(txtCliUsuEstado));
//                valueEstado.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
//                valueEstado.setPaddingTop(valuePaddingTop);
//                valueEstado.setBorder(Rectangle.NO_BORDER);
//                tableDetalle.addCell(valueEstado); // AÑADO A LA TABLA LA CELDA 
                
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
            // Escribir el ByteArrayOutputStream a el ServletOutputStream
            OutputStream os = response.getOutputStream();
            baos.writeTo (os);
            os.flush ();
            os.close ();
        } catch(DocumentException e) {
            throw new IOException(e.getMessage());
        }
    } // END DO_POST
    
} // END CLASS 