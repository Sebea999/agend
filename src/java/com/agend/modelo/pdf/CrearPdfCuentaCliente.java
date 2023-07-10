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
import java.util.ArrayList;
import java.util.Iterator;
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
import com.agend.modelo.ModelRef_Clinica;

/**
 *
 * @author RYUU
 */
@WebServlet(name="crearPdfCuentaPaciente", urlPatterns="/cuenta_paciente_pdf")
public class CrearPdfCuentaCliente extends HttpServlet {
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
                ModelCuentaCliente metodosCuentaCliente = new ModelCuentaCliente();
                ModelRef_Clinica metodosRefClinica = new ModelRef_Clinica();
                List<BeanCuentaCliente> listaDatos = new ArrayList<>();
                
                // PODRIA UTILIZAR LA SESION PERO COMO ESTE SERVLET PARA IMPRIMIR EMPEÑO UTILIZO TAMBIEN EN REPORTES Y AHI DEBE DE SELECCIONAR DE UNA GRILLA SI QUIERE IMPRIMIR, ENTONCES POR ESO PREGUNTO POR EL VALOR DEL CAMPO DE TEXTO, SI ESTA NULO ES PORQUE SE CARGO EL IDEMPENHO POR MEDIO DE LA SESION Y SI NO ESTA NULO ES PORQUE NO SE CARGA POR LA SESION Y POR ESO CARGO EL IDEMPENHO EN UN CAMPO DE TEXTO 
                String idCliente;
                if (String.valueOf(request.getParameter("tIC")) == null || String.valueOf(request.getParameter("tIC")).isEmpty()) {
                    idCliente = (String) sesionDatosUsuario.getAttribute("ID_CLIENTE");
                } else {
                    idCliente = (String) request.getParameter("tIC");
                }
                System.out.println("_PDF____ID_CLIENTE:     :"+idCliente);
                
                // CARGO EL TITULO DE LA PAGINA ------- 
                documento.addTitle("Detalle de la Cuenta del Cliente");
                // CARGO EL TITULO DE LA HOJA --------- 
                String empresa = metodosIniSes.devolverEmpresa();
                Paragraph titulo = new Paragraph(empresa, FontFactory.getFont("arial", 16, Font.BOLD));
                titulo.setAlignment(1); // ALINEO DEL TITULO 
                titulo.setSpacingAfter(10f); // ESPACIO VERTICAL DEL TITULO CON EL PROXIMO ELEMENTO 
                documento.add(titulo);
//                documento.add(Chunk.NEWLINE); // LINEA EN BLANCO 
                
                // CARGO EL SUBTITULO ------------------
                Paragraph subtitulo = new Paragraph("Detalle de las Cuentas", FontFactory.getFont("arial", 15, Font.BOLD));
                subtitulo.setAlignment(1); // ALINEO EL TITULO 
                subtitulo.setSpacingAfter(5f); // ESPACIO VERTICAL DEL TITULO CON EL PROXIMO ELEMENTO 
                documento.add(subtitulo);
                
            // CABECERA ----------------- --------------------- ------------------ ------------------- -------------- 
                DecimalFormat formatear = new DecimalFormat("###,###,###");
                List<BeanPersona> listaCliente = metodosPersona.traerDatosPersona(idCliente);
                Iterator<BeanPersona> iterCliente = listaCliente.iterator();
                String txtClienteCinro="", txtClienteApellido="", txtClienteNombre="", txtClienteDireccion="", txtClienteTelefono="";
                BeanPersona datosCliente = new BeanPersona();
                
                while(iterCliente.hasNext()) {
                    datosCliente = iterCliente.next();
                    
                    txtClienteCinro = datosCliente.getRP_CINRO();
                    txtClienteApellido = datosCliente.getRP_APELLIDO();
                    txtClienteNombre = datosCliente.getRP_NOMBRE();
                    txtClienteDireccion = datosCliente.getRP_DIRECCION();
                    txtClienteTelefono = datosCliente.getRP_TELFMOVIL();
                }
                
                // INSTANCIO LA TABLA CARGANDOLA CON DOS COLUMNAS 
                PdfPTable tablaCliente = new PdfPTable(2);
                // LE CARGO A LA TABLA PARA NO MOSTRAR LOS BORDES --------- ----------
                tablaCliente.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                // LE DEFINO EL ANCHO TOTAL DE LA TABLA 
                tablaCliente.setTotalWidth(400);
                // CREO UN ARREGLO QUE CONTIENE LAS MEDIDAS DE CADA UNA DE LAS COLUMNAS ------- ---------
                float[] medidaCeldaDet = {150, 250};
                // ASIGNAS LAS MEDIDAS A LA TABLA (ANCHO) -------- ---------- 
                tablaCliente.setWidths(medidaCeldaDet);
                // LE ASIGNO LA POSICION DE LA TABLA ----------- ----------- 
                tablaCliente.setHorizontalAlignment(Element.ALIGN_LEFT);
                // VARIABLES QUE UTILIZO PARA ESTANDARIZAR VALORES  ------ ------
                float valuePaddingTop = 10f;
                float valuePaddingRight = 20f;
                // CARGO LAS COLUMNAS DE LA TABLA ------------ ----------- 
                // LABEL, ID_CLIENTE 
                PdfPCell lblIdCliente = new PdfPCell(new Phrase("Código: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblIdCliente.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                lblIdCliente.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
                lblIdCliente.setPaddingTop(valuePaddingTop);
                lblIdCliente.setPaddingRight(valuePaddingRight);
                lblIdCliente.setBorder(Rectangle.NO_BORDER);
                tablaCliente.addCell(lblIdCliente); // AÑADO A LA TABLA LA CELDA 
                // LINEA DEL DATO, ID_CLIENTE 
                PdfPCell valueIdCliente = new PdfPCell(new Phrase(idCliente, FontFactory.getFont("arial", 12, Font.BOLD)));
                valueIdCliente.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                valueIdCliente.setPaddingTop(valuePaddingTop);
                valueIdCliente.setBorder(Rectangle.NO_BORDER);
                tablaCliente.addCell(valueIdCliente); // AÑADO A LA TABLA LA CELDA 
                
                // LABEL, NOMBRE 
                PdfPCell lblNombre = new PdfPCell(new Phrase("Nombre: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblNombre.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                lblNombre.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
                lblNombre.setPaddingTop(valuePaddingTop);
                lblNombre.setPaddingRight(valuePaddingRight);
                lblNombre.setBorder(Rectangle.NO_BORDER);
                tablaCliente.addCell(lblNombre); // AÑADO A LA TABLA LA CELDA 
                // LINEA DEL DATO, NOMBRE 
                PdfPCell valueNombre = new PdfPCell(new Phrase(txtClienteNombre, FontFactory.getFont("arial", 12, Font.NORMAL)));
                valueNombre.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                valueNombre.setPaddingTop(valuePaddingTop);
                valueNombre.setBorder(Rectangle.NO_BORDER);
                tablaCliente.addCell(valueNombre); // AÑADO A LA TABLA LA CELDA 
                
                // LABEL, APELLIDO  
                PdfPCell lblApellido = new PdfPCell(new Phrase("Apellido: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblApellido.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                lblApellido.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
                lblApellido.setPaddingTop(valuePaddingTop);
                lblApellido.setPaddingRight(valuePaddingRight);
                lblApellido.setBorder(Rectangle.NO_BORDER);
                tablaCliente.addCell(lblApellido); // AÑADO A LA TABLA LA CELDA 
                // LINEA DEL DATO, APELLIDO  
                PdfPCell valueApellido = new PdfPCell(new Phrase(txtClienteApellido, FontFactory.getFont("arial", 12, Font.NORMAL)));
                valueApellido.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                valueApellido.setPaddingTop(valuePaddingTop);
                valueApellido.setBorder(Rectangle.NO_BORDER);
                tablaCliente.addCell(valueApellido); // AÑADO A LA TABLA LA CELDA 
                
                // LABEL, CINRO 
                PdfPCell lblCinro = new PdfPCell(new Phrase("Nro. CI: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblCinro.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                lblCinro.setHorizontalAlignment(Element.ALIGN_RIGHT); // CENTRADO HORIZONTAL 
                lblCinro.setPaddingTop(valuePaddingTop);
                lblCinro.setPaddingRight(valuePaddingRight);
                lblCinro.setBorder(Rectangle.NO_BORDER);
                tablaCliente.addCell(lblCinro); // AÑADO A LA TABLA LA CELDA 
                // LINEA DEL DATO, CINRO 
                PdfPCell valueCinro = new PdfPCell(new Phrase(txtClienteCinro, FontFactory.getFont("arial", 12, Font.NORMAL)));
                valueCinro.setVerticalAlignment(Element.ALIGN_MIDDLE); // CENTRADO VERTICAL 
                valueCinro.setPaddingTop(valuePaddingTop);
                valueCinro.setBorder(Rectangle.NO_BORDER);
                tablaCliente.addCell(valueCinro); // AÑADO A LA TABLA LA CELDA 
                
                // LA GRILLA SE PEGA MUCHO POR LA ULTIMA LINEA ENTONCES LE DOY MAS ESPACIO PARA QEU NO SE JUNTE MUCHO 
                tablaCliente.setSpacingBefore(10);
                documento.add(tablaCliente);
                
            // DETALLE ------------------ --------------------- ------------------ ------------------- -------------- 
                listaDatos = metodosCuentaCliente.traer_datos(idCliente, sesionDatosUsuario);
                if ((List<BeanCuentaCliente>) sesionDatosUsuario.getAttribute("CR_RCC_LISTA_FILTRO_CTA") != null) {
                    System.out.println("_   ___IF_____LISTA_CARGADA_____");
                    listaDatos = (List<BeanCuentaCliente>) sesionDatosUsuario.getAttribute("CR_RCC_LISTA_FILTRO_CTA");
                }
                Iterator<BeanCuentaCliente> iterCtaCli = listaDatos.iterator();
                BeanCuentaCliente datosCtaCli = new BeanCuentaCliente();
                String NROCUOTA, DESC_CLINICA, COMENTARIO, FECHA_VENC, MONTO, SALDO, ESTADO;
//                
//                while(iterCtaCli.hasNext()) {
//                    datosCtaCli = iterCtaCli.next();
//                    
//                    NROCUOTA = datosCtaCli.getCC_NROCUOTA();
//                    COMENTARIO = datosCtaCli.getCC_COMENTARIO();
//                    FECHA_VENC = datosCtaCli.getCC_FEC_VENCIMIENTO();
//                    MONTO = datosCtaCli.getCC_MONTO();
//                    SALDO = datosCtaCli.getCC_SALDO();
//                    ESTADO = datosCtaCli.getCC_ESTADO();
//                }
                // CREO LA TABLA 
                PdfPTable tablaCuenta = new PdfPTable(7);
                tablaCuenta.setTotalWidth(750);
                float[] medidaCeldaDetCta = {80, 100, 170, 100, 100, 100, 100};
                tablaCuenta.setWidths(medidaCeldaDetCta);
                tablaCuenta.setHorizontalAlignment(Element.ALIGN_CENTER);
                // VARIABLES QUE UTILIZO COMO MEDIDA GENERAL 
                float float5 = 5f;
                float value10f = 10f;
                float value20f = 20f;
                // COLUMNA NRO CUOTA 
                PdfPCell colNroCuota = new PdfPCell(new Phrase("Cuota", FontFactory.getFont("arial", 11, Font.BOLD)));
                colNroCuota.setVerticalAlignment(1);
                colNroCuota.setHorizontalAlignment(1);
                colNroCuota.setPadding(float5);
                colNroCuota.setPaddingBottom(float5);
                tablaCuenta.addCell(colNroCuota);
                // COLUMNA CLINICA 
                PdfPCell colClinica = new PdfPCell(new Phrase("Clinica", FontFactory.getFont("arial", 11, Font.BOLD)));
                colClinica.setVerticalAlignment(1);
                colClinica.setHorizontalAlignment(1);
                colClinica.setPadding(float5);
                colClinica.setPaddingBottom(float5);
                tablaCuenta.addCell(colClinica);
                // COLUMNA COMENTARIO 
                PdfPCell colComentario = new PdfPCell(new Phrase("Descripción", FontFactory.getFont("arial", 11, Font.BOLD)));
                colComentario.setVerticalAlignment(1);
                colComentario.setHorizontalAlignment(1);
                colComentario.setPadding(float5);
                colComentario.setPaddingBottom(float5);
                tablaCuenta.addCell(colComentario);
                // COLUMNA FECHA_VENC 
                PdfPCell colFechaVenc = new PdfPCell(new Phrase("Fecha Venc.", FontFactory.getFont("arial", 11, Font.BOLD)));
                colFechaVenc.setVerticalAlignment(1);
                colFechaVenc.setHorizontalAlignment(1);
                colFechaVenc.setPadding(float5);
                colFechaVenc.setPaddingBottom(float5);
                tablaCuenta.addCell(colFechaVenc);
                // COLUMNA MONTO 
                PdfPCell colMonto = new PdfPCell(new Phrase("Monto", FontFactory.getFont("arial", 11, Font.BOLD)));
                colMonto.setVerticalAlignment(1);
                colMonto.setHorizontalAlignment(1);
                colMonto.setPadding(float5);
                colMonto.setPaddingBottom(float5);
                tablaCuenta.addCell(colMonto);
                // COLUMNA SALDO 
                PdfPCell colSaldo = new PdfPCell(new Phrase("Saldo", FontFactory.getFont("arial", 11, Font.BOLD)));
                colSaldo.setVerticalAlignment(1);
                colSaldo.setHorizontalAlignment(1);
                colSaldo.setPadding(float5);
                colSaldo.setPaddingBottom(float5);
                tablaCuenta.addCell(colSaldo);
                // COLUMNA ESTADO 
                PdfPCell colEstado = new PdfPCell(new Phrase("Estado", FontFactory.getFont("arial", 11, Font.BOLD)));
                colEstado.setVerticalAlignment(1);
                colEstado.setHorizontalAlignment(1);
                colEstado.setPadding(float5);
                colEstado.setPaddingBottom(float5);
                tablaCuenta.addCell(colEstado);
                // CARGO EL DETALLE DE CADA COLUMNA 
//                for (BeanCuentaCliente detalleCtas : listaDatos) {
//                    
//                } // end for 
                
                //  VARIABLES QUE UTILIZO COMO TOTALES DE LA PAGINA PERO A LOS ESTADOS ACTIVOS Y COBRADOS 
                double totalMonto = 0;
                double totalSaldo = 0;
                
                while(iterCtaCli.hasNext()) {
                    datosCtaCli = iterCtaCli.next();
                    
                    NROCUOTA = datosCtaCli.getCC_NROCUOTA();
                    String IDCLINICA = datosCtaCli.getOA_IDCLINICA();
                    DESC_CLINICA = metodosRefClinica.traerDescClinica(IDCLINICA);
                    COMENTARIO = datosCtaCli.getCC_COMENTARIO();
                    FECHA_VENC = datosCtaCli.getCC_FEC_VENCIMIENTO();
                    MONTO = datosCtaCli.getCC_MONTO();
                    SALDO = datosCtaCli.getCC_SALDO();
                    ESTADO = datosCtaCli.getCC_ESTADO();
                    
                    System.out.println("--------__DATOS__--------");
                    System.out.println("__NRO_CUOTA:    :"+NROCUOTA);
                    System.out.println("__DESC_CLINI:   :"+DESC_CLINICA);
                    System.out.println("__COMENTARIO:   :"+COMENTARIO);
                    System.out.println("__FECHA_VENC:   :"+FECHA_VENC);
                    System.out.println("__MONTO:  :"+MONTO);
                    System.out.println("__SALDO:  :"+SALDO);
                    System.out.println("__ESTADO: :"+ESTADO);
                    System.out.println("-------------------------");
                    
                    // CONDICIONAL PARA QUE SI EL ESTADO ES ANULADO EL MONTO Y SALDO SEA CERO Y LOS TOTALES NO LOS CUENTE 
                    if (ESTADO.equalsIgnoreCase("X") || ESTADO.equalsIgnoreCase("ANULADO")) {
                        MONTO = "0";
                        SALDO = "0";
                    } else {
                        totalMonto = totalMonto + Double.parseDouble(MONTO.replace(".",""));
                        totalSaldo = totalSaldo + Double.parseDouble(SALDO.replace(".",""));
//                    } // end condicional estado_anulado
                    // OBSERVACION SOBRE LA CONDICIONAL DEL ESTADO ANULADO: 
                    // COMENTE EL CIERRE DE LA CONDICIONAL QUE PREGUNTA POR EL ESTADO DE LAS CUENTAS PARA QUE AL IMPRIMIR EL PDF NO SE MUESTRE EN LA GRILLA LAS CUENTAS ANULADAS / Y EN CASO DE QUE EN UN FUTURO QUIERA MOSTRAR, ENTONCES COMENTARIA EL CIERRE DE ABAJO Y DESCOMENTARIA EL CIERRE DE ARRIBA 
                        // NRO CUOTA 
                        PdfPCell cellNroCuota = new PdfPCell(new Phrase(NROCUOTA, FontFactory.getFont("arial", 9, Font.NORMAL)));
                        cellNroCuota.setUseAscender(true);
                        cellNroCuota.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cellNroCuota.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cellNroCuota.setPadding(float5);
                        cellNroCuota.setBorder(Rectangle.BOTTOM);
                        cellNroCuota.setBorderColor(BaseColor.GRAY);
                        tablaCuenta.addCell(cellNroCuota);
                        // CLINICA 
                        PdfPCell cellClinica = new PdfPCell(new Phrase(DESC_CLINICA, FontFactory.getFont("arial", 9, Font.NORMAL)));
                        cellClinica.setUseAscender(true);
                        cellClinica.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cellClinica.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cellClinica.setPadding(float5);
                        cellClinica.setBorder(Rectangle.BOTTOM);
                        cellClinica.setBorderColor(BaseColor.GRAY);
                        tablaCuenta.addCell(cellClinica);
                        // COMENTARIO 
                        PdfPCell cellComentario = new PdfPCell(new Phrase(COMENTARIO, FontFactory.getFont("arial", 9, Font.NORMAL)));
                        cellComentario.setUseAscender(true);
                        cellComentario.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cellComentario.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cellComentario.setPadding(float5);
                        cellComentario.setBorder(Rectangle.BOTTOM);
                        cellComentario.setBorderColor(BaseColor.GRAY);
                        tablaCuenta.addCell(cellComentario);
                        // FECHA_VENCIMIENTO 
                        PdfPCell cellFechaVenc = new PdfPCell(new Phrase(FECHA_VENC, FontFactory.getFont("arial", 9, Font.NORMAL)));
                        cellFechaVenc.setUseAscender(true);
                        cellFechaVenc.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cellFechaVenc.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cellFechaVenc.setPadding(float5);
                        cellFechaVenc.setBorder(Rectangle.BOTTOM);
                        cellFechaVenc.setBorderColor(BaseColor.GRAY);
                        tablaCuenta.addCell(cellFechaVenc);
                        // MONTO 
                        PdfPCell cellMonto = new PdfPCell(new Phrase(MONTO, FontFactory.getFont("arial", 9, Font.NORMAL)));
                        cellMonto.setUseAscender(true);
                        cellMonto.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cellMonto.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cellMonto.setPadding(float5);
                        cellMonto.setBorder(Rectangle.BOTTOM);
                        cellMonto.setBorderColor(BaseColor.GRAY);
                        tablaCuenta.addCell(cellMonto);
                        // SALDO 
                        PdfPCell cellSaldo = new PdfPCell(new Phrase(SALDO, FontFactory.getFont("arial", 9, Font.NORMAL)));
                        cellSaldo.setUseAscender(true);
                        cellSaldo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cellSaldo.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cellSaldo.setPadding(float5);
                        cellSaldo.setBorder(Rectangle.BOTTOM);
                        cellSaldo.setBorderColor(BaseColor.GRAY);
                        tablaCuenta.addCell(cellSaldo);
                        // ESTADO 
                        PdfPCell cellEstado = new PdfPCell(new Phrase(ESTADO, FontFactory.getFont("arial", 9, Font.NORMAL)));
                        cellEstado.setUseAscender(true);
                        cellEstado.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cellEstado.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cellEstado.setPadding(float5);
                        cellEstado.setBorder(Rectangle.BOTTOM);
                        cellEstado.setBorderColor(BaseColor.GRAY);
                        tablaCuenta.addCell(cellEstado);
                    } // end condicional estado_anulado
                } // end while 
                tablaCuenta.setSpacingBefore(15);
                documento.add(tablaCuenta);
                
                // TABLA PARA EL PIE DE PAGINA CON LOS TOTALES ----------------- 
                PdfPTable tablaTotales = new PdfPTable(2);
//                tablaTotales.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                tablaTotales.setTotalWidth(200);
                float[] medidaCeldasTotales = {60, 140};
                tablaTotales.setWidths(medidaCeldasTotales);
                tablaTotales.setHorizontalAlignment(Element.ALIGN_LEFT);
                tablaTotales.setWidthPercentage(100);
                // DATOS DE LA TABLA 
                // LABEL, TOTALES 
                PdfPCell lblTotales = new PdfPCell(new Phrase("Totales: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblTotales.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblTotales.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblTotales.setPaddingTop(value10f);
                lblTotales.setPaddingRight(value20f);
                lblTotales.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(lblTotales);
                // VALUE, TOTALES / OBS.: LO DEJO VACIO YA QUE TOTALES QUIERO UTILIZARLO COMO UN SUBTITULO Y POR ESO LE CARGO UN VALOR VACIO PARA QUE ASI EL SIGUIENTE LABEL NO SE COLOQUE ALADO DE ESTE SINO ABAJO  
                PdfPCell valueTotalesVacio = new PdfPCell(new Phrase(" ", FontFactory.getFont("arial", 12, Font.BOLD)));
                valueTotalesVacio.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(valueTotalesVacio);
                // LABEL, TOTAL_MONTO 
                PdfPCell lblTotMonto = new PdfPCell(new Phrase("Total Monto: ", FontFactory.getFont("arial", 11, Font.BOLD)));
                lblTotMonto.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblTotMonto.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblTotMonto.setPaddingTop(value10f);
                lblTotMonto.setPaddingRight(value20f);
                lblTotMonto.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(lblTotMonto);
                // VALUE, TOTAL_MONTO 
                PdfPCell valueTotMonto = new PdfPCell(new Phrase(formatear.format(totalMonto), FontFactory.getFont("arial", 12, Font.NORMAL)));
                valueTotMonto.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueTotMonto.setHorizontalAlignment(Element.ALIGN_LEFT);
                valueTotMonto.setPaddingTop(value10f);
                valueTotMonto.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(valueTotMonto);
                // LABEL, TOTAL_SALDO 
                PdfPCell lblTotSaldo = new PdfPCell(new Phrase("Total Saldo: ", FontFactory.getFont("arial", 11, Font.BOLD)));
                lblTotSaldo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblTotSaldo.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblTotSaldo.setPaddingTop(value10f);
                lblTotSaldo.setPaddingRight(value20f);
                lblTotSaldo.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(lblTotSaldo);
                // VALUE, TOTAL_SALDO 
                PdfPCell valueTotSaldo = new PdfPCell(new Phrase(formatear.format(totalSaldo), FontFactory.getFont("arial", 12, Font.NORMAL)));
                valueTotSaldo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueTotSaldo.setHorizontalAlignment(Element.ALIGN_LEFT);
                valueTotSaldo.setPaddingTop(value10f);
                valueTotSaldo.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(valueTotSaldo);
                
                tablaTotales.setSpacingBefore(15);
                documento.add(tablaTotales);
                
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