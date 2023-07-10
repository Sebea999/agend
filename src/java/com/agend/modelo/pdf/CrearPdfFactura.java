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
@WebServlet(name="crearPdfFactura", urlPatterns="/factura_cliente_pdf")
public class CrearPdfFactura extends HttpServlet {
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    } // END DO_GET 
    
    
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
                
                HttpSession sesionDatosUsuario = request.getSession();
                DecimalFormat formatear = new DecimalFormat("###,###,###");
                ModelInicioSesion metodosIniSes = new ModelInicioSesion();
                ModelPersona metodosPersona = new ModelPersona();
                ModelFactura metodosFactura = new ModelFactura();
                List<BeanFacturaCab> listaDatos = new ArrayList<>();
                
                // PODRIA UTILIZAR LA SESION PERO COMO ESTE SERVLET PARA IMPRIMIR EMPEÑO UTILIZO TAMBIEN EN REPORTES Y AHI DEBE DE SELECCIONAR DE UNA GRILLA SI QUIERE IMPRIMIR, ENTONCES POR ESO PREGUNTO POR EL VALOR DEL CAMPO DE TEXTO, SI ESTA NULO ES PORQUE SE CARGO EL IDEMPENHO POR MEDIO DE LA SESION Y SI NO ESTA NULO ES PORQUE NO SE CARGA POR LA SESION Y POR ESO CARGO EL IDEMPENHO EN UN CAMPO DE TEXTO 
                String idFactura;
                if (String.valueOf(request.getParameter("tRIF")) == null || String.valueOf(request.getParameter("tRIF")).isEmpty()) {
                    idFactura = (String) sesionDatosUsuario.getAttribute("ID_FACTURA");
                } else {
                    idFactura = (String) request.getParameter("tRIF");
                }
                
                documento.addTitle("Reporte Factura");
                String empresa = metodosIniSes.devolverEmpresa();
                Paragraph titulo = new Paragraph(empresa, FontFactory.getFont("arial", 16, Font.BOLD));
                titulo.setAlignment(1);
                titulo.setSpacingAfter(10f);
                documento.add(titulo);
                
            // DETALLE -------------- ----------------- ---------------- -------------- ----------------- ----------------
                listaDatos = metodosFactura.traer_datos(idFactura, sesionDatosUsuario);
                Iterator<BeanFacturaCab> iterFac = listaDatos.iterator();
                BeanFacturaCab datos = new BeanFacturaCab();
                String TXT_CLIENTE_ID="", TXT_CLIENTE_CINRO="", TXT_CLIENTE_NAME="", TXT_CLIENTE_RS="", TXT_CLIENTE_RUC="", TXT_NRO_FACTURA="", CBX_TIPO_FACTURA="", TXT_FECHA_FACTURA="", TXT_TOTAL_IVA5="", TXT_TOTAL_IVA10="", TXT_TOTAL_GRAV5="", TXT_TOTAL_GRAV10="", TXT_TOTAL_IVA="", TXT_TOTAL="";
                List<BeanFacturaCab> listaDetalle = new ArrayList<>();
                
                while(iterFac.hasNext()) {
                    datos = iterFac.next();
                    
                    // CARGO LA CABECERA 
                    TXT_CLIENTE_ID = String.valueOf(datos.getOF_IDCLIENTE());
                    TXT_NRO_FACTURA = datos.getOF_NROFACTURA();
                    CBX_TIPO_FACTURA = datos.getOF_TIPOFACTURA();
                    if ((CBX_TIPO_FACTURA == null) == false || (CBX_TIPO_FACTURA.isEmpty()) == false) {
                        if (CBX_TIPO_FACTURA.equalsIgnoreCase("Cr")) {
                            CBX_TIPO_FACTURA = "Crédito";
                        } else if (CBX_TIPO_FACTURA.equalsIgnoreCase("Co")) {
                            CBX_TIPO_FACTURA = "Contado";
                        } else {
                            CBX_TIPO_FACTURA = "Contado";
                        }
                    }
                    TXT_FECHA_FACTURA = metodosIniSes.getDatoFecha(0, datos.getOF_FECHAFACTURA());
                    TXT_TOTAL_IVA5 = formatear.format(Double.parseDouble(datos.getOF_TOTAL_IVA5()));
                    TXT_TOTAL_IVA10 = formatear.format(Double.parseDouble(datos.getOF_TOTAL_IVA10()));
                    TXT_TOTAL_GRAV5 = formatear.format(Double.parseDouble(datos.getOF_TOTAL_GRAV5()));
                    TXT_TOTAL_GRAV10 = formatear.format(Double.parseDouble(datos.getOF_TOTAL_GRAV10()));
                    TXT_TOTAL_IVA = formatear.format(Double.parseDouble(datos.getOF_TOTAL_IVA()));
                    TXT_TOTAL = formatear.format(Double.parseDouble(datos.getOF_TOTAL_FACTURA()));
                    
                    BeanFacturaCab datos_det = new BeanFacturaCab();
                        datos_det.setOFD_ITEM(datos.getOFD_ITEM());
                        datos_det.setOFD_IDCONCEPTO(datos.getOFD_IDCONCEPTO());
                        datos_det.setOFD_IDTIPOCONCEPTO(datos.getOFD_IDTIPOCONCEPTO());
                        datos_det.setOFD_DESCRIPCION_AUX(datos.getOFD_DESCRIPCION_AUX());
                        datos_det.setOFD_CANTIDAD(datos.getOFD_CANTIDAD());
                        datos_det.setOFD_PRECIO(formatear.format(Double.parseDouble(datos.getOFD_PRECIO())));
                        datos_det.setOFD_IDIMPUESTO(datos.getOFD_IDIMPUESTO());
                        datos_det.setOFD_EXENTO(formatear.format(Double.parseDouble(datos.getOFD_EXENTO())));
                        datos_det.setOFD_IVA5(formatear.format(Double.parseDouble(datos.getOFD_IVA5())));
                        datos_det.setOFD_IVA10(formatear.format(Double.parseDouble(datos.getOFD_IVA10())));
                        datos_det.setOFD_GRAV5(formatear.format(Double.parseDouble(datos.getOFD_GRAV5())));
                        datos_det.setOFD_GRAV10(formatear.format(Double.parseDouble(datos.getOFD_GRAV10())));
                        datos_det.setOFD_SUBTOTAL(formatear.format(Double.parseDouble(datos.getOFD_SUBTOTAL())));
                    listaDetalle.add(datos_det);
                } // end while 
                
                // OBTENGO LOS DATOS DEL CLIENTE 
                BeanPersona datosCliente = new BeanPersona();
                datosCliente = metodosPersona.datosBasicosPersona(datosCliente, TXT_CLIENTE_ID);
                TXT_CLIENTE_CINRO = datosCliente.getRP_CINRO();
                TXT_CLIENTE_NAME = datosCliente.getRP_NOMBRE()+" "+datosCliente.getRP_APELLIDO();
                TXT_CLIENTE_RS = datosCliente.getRP_RAZON_SOCIAL();
                TXT_CLIENTE_RUC = datosCliente.getRP_RUC();
                
                // SUBTITULO DE LA PAGINA 
                Paragraph subtitulo = new Paragraph("Detalle de la Factura", FontFactory.getFont("arial", 15, Font.BOLD));
                subtitulo.setAlignment(1);
                subtitulo.setSpacingAfter(10f);
                documento.add(subtitulo);
                
                // INSTANCIO LA TABLA CARGANDOLA CON DOS COLUMNAS 
                PdfPTable tablaCab = new PdfPTable(2);
                tablaCab.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                tablaCab.setTotalWidth(400);
                float[] medidaCeldaCab = {150, 250};
                tablaCab.setWidths(medidaCeldaCab);
                tablaCab.setHorizontalAlignment(Element.ALIGN_LEFT);
                float valuePaddingTop = 10f; // variable con valor para estandarizar cuando quiera cambiar el valor 
                float valuePaddingRight = 20f;
                // DATOS 
                // label NRO_FACTURA 
                PdfPCell lblNroFactura = new PdfPCell(new Phrase("Nro. Factura: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblNroFactura.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblNroFactura.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblNroFactura.setPaddingTop(valuePaddingTop);
                lblNroFactura.setPaddingRight(valuePaddingRight);
                lblNroFactura.setBorder(Rectangle.NO_BORDER);
                tablaCab.addCell(lblNroFactura);
                // value NRO_FACTURA 
                PdfPCell valueNroFactura = new PdfPCell(new Phrase(TXT_NRO_FACTURA, FontFactory.getFont("arial", 12, Font.NORMAL)));
                valueNroFactura.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueNroFactura.setPaddingTop(valuePaddingTop);
                valueNroFactura.setBorder(Rectangle.NO_BORDER);
                tablaCab.addCell(valueNroFactura);
                // label TIPO_FACTURA 
                PdfPCell lblTipoFactura = new PdfPCell(new Phrase("Tipo Factura: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblTipoFactura.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblTipoFactura.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblTipoFactura.setPaddingTop(valuePaddingTop);
                lblTipoFactura.setPaddingRight(valuePaddingRight);
                lblTipoFactura.setBorder(Rectangle.NO_BORDER);
                tablaCab.addCell(lblTipoFactura);
                // value TIPO_FACTURA 
                PdfPCell valueTipoFactura = new PdfPCell(new Phrase(CBX_TIPO_FACTURA, FontFactory.getFont("arial", 12, Font.NORMAL)));
                valueTipoFactura.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueTipoFactura.setPaddingTop(valuePaddingTop);
                valueTipoFactura.setBorder(Rectangle.NO_BORDER);
                tablaCab.addCell(valueTipoFactura);
                // label FECHA_FACTURA 
                PdfPCell lblFechaFactura = new PdfPCell(new Phrase("Fecha: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblFechaFactura.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblFechaFactura.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblFechaFactura.setPaddingTop(valuePaddingTop);
                lblFechaFactura.setPaddingRight(valuePaddingRight);
                lblFechaFactura.setBorder(Rectangle.NO_BORDER);
                tablaCab.addCell(lblFechaFactura);
                // value FECHA_FACTURA 
                PdfPCell valueFechaFactura = new PdfPCell(new Phrase(TXT_FECHA_FACTURA, FontFactory.getFont("arial", 12, Font.NORMAL)));
                valueFechaFactura.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueFechaFactura.setPaddingTop(valuePaddingTop);
                valueFechaFactura.setBorder(Rectangle.NO_BORDER);
                tablaCab.addCell(valueFechaFactura);
                // label NAME_CLIENTE  
                PdfPCell lblNameCliente = new PdfPCell(new Phrase("Cliente: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblNameCliente.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblNameCliente.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblNameCliente.setPaddingTop(valuePaddingTop);
                lblNameCliente.setPaddingRight(valuePaddingRight);
                lblNameCliente.setBorder(Rectangle.NO_BORDER);
                tablaCab.addCell(lblNameCliente);
                // value NAME_CLIENTE 
                PdfPCell valueNameCliente = new PdfPCell(new Phrase(TXT_CLIENTE_NAME, FontFactory.getFont("arial", 12, Font.NORMAL)));
                valueNameCliente.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueNameCliente.setPaddingTop(valuePaddingTop);
                valueNameCliente.setBorder(Rectangle.NO_BORDER);
                tablaCab.addCell(valueNameCliente);
                // label CINRO_CLIENTE 
                PdfPCell lblCinroCliente = new PdfPCell(new Phrase("Nro. CI: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblCinroCliente.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblCinroCliente.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblCinroCliente.setPaddingTop(valuePaddingTop);
                lblCinroCliente.setPaddingRight(valuePaddingRight);
                lblCinroCliente.setBorder(Rectangle.NO_BORDER);
                tablaCab.addCell(lblCinroCliente);
                // value CINRO_CLIENTE 
                PdfPCell valueCinroCliente = new PdfPCell(new Phrase(TXT_CLIENTE_CINRO, FontFactory.getFont("arial", 12, Font.NORMAL)));
                valueCinroCliente.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueCinroCliente.setPaddingTop(valuePaddingTop);
                valueCinroCliente.setBorder(Rectangle.NO_BORDER);
                tablaCab.addCell(valueCinroCliente);
                // label RAZON_SOCIAL_CLIENTE 
                PdfPCell lblRazonSocial = new PdfPCell(new Phrase("Razón Social: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblRazonSocial.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblRazonSocial.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblRazonSocial.setPaddingTop(valuePaddingTop);
                lblRazonSocial.setPaddingRight(valuePaddingRight);
                lblRazonSocial.setBorder(Rectangle.NO_BORDER);
                tablaCab.addCell(lblRazonSocial);
                // value RAZON_SOCIAL_CLIENTE
                PdfPCell valueRazonSocial = new PdfPCell(new Phrase(TXT_CLIENTE_RS, FontFactory.getFont("arial", 12, Font.NORMAL)));
                valueRazonSocial.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueRazonSocial.setPaddingTop(valuePaddingTop);
                valueRazonSocial.setBorder(Rectangle.NO_BORDER);
                tablaCab.addCell(valueRazonSocial);
                // label RUC_CLIENTE 
                PdfPCell lblRucCliente = new PdfPCell(new Phrase("RUC: ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblRucCliente.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblRucCliente.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblRucCliente.setPaddingTop(valuePaddingTop);
                lblRucCliente.setPaddingRight(valuePaddingRight);
                lblRucCliente.setBorder(Rectangle.NO_BORDER);
                tablaCab.addCell(lblRucCliente);
                // value RUC_CLIENTE 
                PdfPCell valueRucCliente = new PdfPCell(new Phrase(TXT_CLIENTE_RUC, FontFactory.getFont("arial", 12, Font.NORMAL)));
                valueRucCliente.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueRucCliente.setPaddingTop(valuePaddingTop);
                valueRucCliente.setBorder(Rectangle.NO_BORDER);
                tablaCab.addCell(valueRucCliente);
                
                tablaCab.setSpacingBefore(15);
                documento.add(tablaCab);
                
                // TABLA CON EL DETALLE DE LA FACTURA -------------------------- 
                PdfPTable tablaDet = new PdfPTable(7);
                tablaDet.setTotalWidth(750);
                float[] medidaCeldaDet = {70, 180, 100, 70, 70, 120, 140};
                tablaDet.setWidths(medidaCeldaDet);
                tablaDet.setHorizontalAlignment(Element.ALIGN_CENTER);
                // VARIABLES PARA ESTANDARIZAR VALORES 
                float value5f = 5f;
                float value10f = 10f;
                float value20f = 20f;
                float valPadding = 5f;
                float valPaddingTop = 5f;
                float valPaddingBottom = 5f;
                // COLUMNA NRO_ITEM 
                PdfPCell colNroItem = new PdfPCell(new Phrase("Item", FontFactory.getFont("arial", 12, Font.BOLD)));
                colNroItem.setVerticalAlignment(1);
                colNroItem.setHorizontalAlignment(1);
                colNroItem.setPadding(valPadding);
                colNroItem.setPaddingTop(valPaddingTop);
                colNroItem.setPaddingBottom(valPaddingBottom);
                tablaDet.addCell(colNroItem);
                // COLUMNA DESCRIPCION 
                PdfPCell colDescripcion = new PdfPCell(new Phrase("Descripción", FontFactory.getFont("arial", 12, Font.BOLD)));
                colDescripcion.setVerticalAlignment(1);
                colDescripcion.setHorizontalAlignment(1);
                colDescripcion.setPadding(valPadding);
                colDescripcion.setPaddingTop(valPaddingTop);
                colDescripcion.setPaddingBottom(valPaddingBottom);
                tablaDet.addCell(colDescripcion);
                // COLUMNA PRECIO 
                PdfPCell colPrecio = new PdfPCell(new Phrase("Precio", FontFactory.getFont("arial", 12, Font.BOLD)));
                colPrecio.setVerticalAlignment(1);
                colPrecio.setHorizontalAlignment(1);
                colPrecio.setPadding(valPadding);
                colPrecio.setPaddingTop(valPaddingTop);
                colPrecio.setPaddingBottom(valPaddingBottom);
                tablaDet.addCell(colPrecio);
                // COLUMNA CANTIDAD 
                PdfPCell colCantidad = new PdfPCell(new Phrase("Cant.", FontFactory.getFont("arial", 12, Font.BOLD)));
                colCantidad.setVerticalAlignment(1);
                colCantidad.setHorizontalAlignment(1);
                colCantidad.setPadding(valPadding);
                colCantidad.setPaddingTop(valPaddingTop);
                colCantidad.setPaddingBottom(valPaddingBottom);
                tablaDet.addCell(colCantidad);
                // COLUMNA IVA 
                PdfPCell colIva = new PdfPCell(new Phrase("IVA", FontFactory.getFont("arial", 12, Font.BOLD)));
                colIva.setVerticalAlignment(1);
                colIva.setHorizontalAlignment(1);
                colIva.setPadding(valPadding);
                colIva.setPaddingTop(valPaddingTop);
                colIva.setPaddingBottom(valPaddingBottom);
                tablaDet.addCell(colIva);
                // COLUMNA MONTO_IVA 
                PdfPCell colMontoIva = new PdfPCell(new Phrase("Monto IVA", FontFactory.getFont("arial", 12, Font.BOLD)));
                colMontoIva.setVerticalAlignment(1);
                colMontoIva.setHorizontalAlignment(1);
                colMontoIva.setPadding(valPadding);
                colMontoIva.setPaddingTop(valPaddingTop);
                colMontoIva.setPaddingBottom(valPaddingBottom);
                tablaDet.addCell(colMontoIva);
                // COLUMNA SUBTOTAL 
                PdfPCell colSubtotal = new PdfPCell(new Phrase("SubTotal", FontFactory.getFont("arial", 12, Font.BOLD)));
                colSubtotal.setVerticalAlignment(1);
                colSubtotal.setHorizontalAlignment(1);
                colSubtotal.setPadding(valPadding);
                colSubtotal.setPaddingTop(valPaddingTop);
                colSubtotal.setPaddingBottom(valPaddingBottom);
                tablaDet.addCell(colSubtotal);
                
                for (BeanFacturaCab detalleDatos : listaDetalle) {
                    String NRO_ITEM = String.valueOf(detalleDatos.getOFD_ITEM());
                    String DESCRIPCION = detalleDatos.getOFD_DESCRIPCION_AUX();
                    String PRECIO = detalleDatos.getOFD_PRECIO();
                    String CANTIDAD = String.valueOf(detalleDatos.getOFD_CANTIDAD());
                    String IVA = detalleDatos.getOFD_IDIMPUESTO();
                    String MONTO_IVA="0";
                    if (IVA.equalsIgnoreCase("10") || IVA.equalsIgnoreCase("10%")) {
                        IVA = IVA+"%";
                        MONTO_IVA = detalleDatos.getOFD_IVA10();
                    } else if(IVA.equalsIgnoreCase("5") || IVA.equalsIgnoreCase("5%")) {
                        IVA = IVA+"%";
                        MONTO_IVA = detalleDatos.getOFD_IVA5();
                    }
                    String SUBTOTAL = detalleDatos.getOFD_SUBTOTAL();
                    
                    System.out.println("--------DATOS_DET--------------");
                    System.out.println("-   _NRO_ITEM:   :"+NRO_ITEM);
                    System.out.println("-   _DESCRIP.:   :"+DESCRIPCION);
                    System.out.println("-   ___PRECIO:   :"+PRECIO);
                    System.out.println("-   _CANTIDAD:   :"+CANTIDAD);
                    System.out.println("-   _______IVA:   :"+IVA);
                    System.out.println("-   _MONTO_IVA:   :"+MONTO_IVA);
                    System.out.println("-   __SUBTOTAL:   :"+SUBTOTAL);
                    System.out.println("-------------------------------");
                    
                    // NRO_ITEM 
                    PdfPCell cellNroItem = new PdfPCell(new Phrase(NRO_ITEM, FontFactory.getFont("arial", 11, Font.NORMAL)));
                    cellNroItem.setUseAscender(true);
                    cellNroItem.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellNroItem.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellNroItem.setPadding(value5f);
                    cellNroItem.setBorder(Rectangle.BOTTOM);
                    cellNroItem.setBorderColor(BaseColor.GRAY);
                    tablaDet.addCell(cellNroItem);
                    // DESCRIPCION 
                    PdfPCell cellDescripcion = new PdfPCell(new Phrase(DESCRIPCION, FontFactory.getFont("arial", 10, Font.NORMAL)));
                    cellDescripcion.setUseAscender(true);
                    cellDescripcion.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellDescripcion.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cellDescripcion.setPadding(value5f);
                    cellDescripcion.setBorder(Rectangle.BOTTOM);
                    cellDescripcion.setBorderColor(BaseColor.GRAY);
                    tablaDet.addCell(cellDescripcion);
                    // PRECIO  
                    PdfPCell cellPrecio = new PdfPCell(new Phrase(PRECIO, FontFactory.getFont("arial", 10, Font.NORMAL)));
                    cellPrecio.setUseAscender(true);
                    cellPrecio.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellPrecio.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cellPrecio.setPadding(value5f);
                    cellPrecio.setBorder(Rectangle.BOTTOM);
                    cellPrecio.setBorderColor(BaseColor.GRAY);
                    tablaDet.addCell(cellPrecio);
                    // CANTIDAD 
                    PdfPCell cellCantidad = new PdfPCell(new Phrase(CANTIDAD, FontFactory.getFont("arial", 11, Font.NORMAL)));
                    cellCantidad.setUseAscender(true);
                    cellCantidad.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellCantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellCantidad.setPadding(value5f);
                    cellCantidad.setBorder(Rectangle.BOTTOM);
                    cellCantidad.setBorderColor(BaseColor.GRAY);
                    tablaDet.addCell(cellCantidad);
                    // IVA 
                    PdfPCell cellIva = new PdfPCell(new Phrase(IVA, FontFactory.getFont("arial", 11, Font.NORMAL)));
                    cellIva.setUseAscender(true);
                    cellIva.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellIva.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellIva.setPadding(value5f);
                    cellIva.setBorder(Rectangle.BOTTOM);
                    cellIva.setBorderColor(BaseColor.GRAY);
                    tablaDet.addCell(cellIva);
                    // MONTO_IVA 
                    PdfPCell cellMontoIva = new PdfPCell(new Phrase(MONTO_IVA, FontFactory.getFont("arial", 10, Font.NORMAL)));
                    cellMontoIva.setUseAscender(true);
                    cellMontoIva.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellMontoIva.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cellMontoIva.setPadding(value5f);
                    cellMontoIva.setBorder(Rectangle.BOTTOM);
                    cellMontoIva.setBorderColor(BaseColor.GRAY);
                    tablaDet.addCell(cellMontoIva);
                    // SUBTOTAL 
                    PdfPCell cellSubtotal = new PdfPCell(new Phrase(SUBTOTAL, FontFactory.getFont("arial", 10, Font.NORMAL)));
                    cellSubtotal.setUseAscender(true);
                    cellSubtotal.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellSubtotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cellSubtotal.setPadding(value5f);
                    cellSubtotal.setBorder(Rectangle.BOTTOM);
                    cellSubtotal.setBorderColor(BaseColor.GRAY);
                    tablaDet.addCell(cellSubtotal);
                } // END FOR 
                
                tablaDet.setSpacingBefore(15);
                documento.add(tablaDet);
                
                // TABLA PARA EL PIE DE PAGINA CON LOS TOTALES DE LA FACTURA -------------------------- 
                PdfPTable tablaTotales = new PdfPTable(2);
//                tablaTotales.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                tablaTotales.setTotalWidth(200);
                float[] medidaCeldasTotales = {60, 140};
                tablaTotales.setWidths(medidaCeldasTotales);
                tablaTotales.setHorizontalAlignment(Element.ALIGN_LEFT);
                tablaTotales.setWidthPercentage(100);
                // DATOS DE LA TABLA 
                // LABEL, LIQUIDACION IVA 
                PdfPCell lblLiqIva = new PdfPCell(new Phrase("Liquidación IVA ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblLiqIva.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblLiqIva.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblLiqIva.setPaddingTop(value10f);
                lblLiqIva.setPaddingRight(value20f);
                lblLiqIva.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(lblLiqIva);
                // VALUE, LIQUIDACION IVA / OBS.: LO DEJO VACIO YA QUE LIQUIDACION IVA QUIERO UTILIZARLO COMO UN SUBTITULO Y POR ESO LE CARGO UN VALOR VACIO PARA QUE ASI EL SIGUIENTE LABEL NO SE COLOQUE ALADO DE ESTE SINO ABAJO  
                PdfPCell lblLiqIvaVacio = new PdfPCell(new Phrase(" ", FontFactory.getFont("arial", 12, Font.BOLD)));
                lblLiqIvaVacio.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(lblLiqIvaVacio);
                // LABEL, TOTAL_IVA_5 
                PdfPCell lblIva5 = new PdfPCell(new Phrase("(5%): ", FontFactory.getFont("arial", 11, Font.BOLD)));
                lblIva5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblIva5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblIva5.setPaddingTop(value10f);
                lblIva5.setPaddingRight(value20f);
                lblIva5.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(lblIva5);
                // VALUE, TOTAL_IVA_5 
                PdfPCell valueIva5 = new PdfPCell(new Phrase(TXT_TOTAL_IVA5, FontFactory.getFont("arial", 12, Font.NORMAL)));
                valueIva5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueIva5.setHorizontalAlignment(Element.ALIGN_LEFT);
                valueIva5.setPaddingTop(value10f);
                valueIva5.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(valueIva5);
                // LABEL, TOTAL_IVA_10 
                PdfPCell lblIva10 = new PdfPCell(new Phrase("(10%): ", FontFactory.getFont("arial", 11, Font.BOLD)));
                lblIva10.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblIva10.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblIva10.setPaddingTop(value10f);
                lblIva10.setPaddingRight(value20f);
                lblIva10.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(lblIva10);
                // VALUE, TOTAL_IVA_10 
                PdfPCell valueIva10 = new PdfPCell(new Phrase(TXT_TOTAL_IVA10, FontFactory.getFont("arial", 12, Font.NORMAL)));
                valueIva10.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueIva10.setHorizontalAlignment(Element.ALIGN_LEFT);
                valueIva10.setPaddingTop(value10f);
                valueIva10.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(valueIva10);
                // LABEL, TOTAL_IVA 
                PdfPCell lblIva = new PdfPCell(new Phrase("Total IVA: ", FontFactory.getFont("arial", 11, Font.BOLD)));
                lblIva.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lblIva.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lblIva.setPaddingTop(value10f);
                lblIva.setPaddingRight(value20f);
                lblIva.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(lblIva);
                // VALUE, TOTAL_IVA 
                PdfPCell valueIva = new PdfPCell(new Phrase(TXT_TOTAL_IVA, FontFactory.getFont("arial", 12, Font.NORMAL)));
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
                PdfPCell valueTotalIva = new PdfPCell(new Phrase(TXT_TOTAL, FontFactory.getFont("arial", 12, Font.NORMAL)));
                valueTotalIva.setVerticalAlignment(Element.ALIGN_MIDDLE);
                valueTotalIva.setHorizontalAlignment(Element.ALIGN_LEFT);
                valueTotalIva.setPaddingTop(value10f);
                valueTotalIva.setBorder(Rectangle.NO_BORDER);
                tablaTotales.addCell(valueTotalIva);
                
                tablaTotales.setSpacingBefore(15);
                documento.add(tablaTotales);
                
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