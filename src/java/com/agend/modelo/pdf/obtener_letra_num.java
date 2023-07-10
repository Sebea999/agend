
package com.agend.modelo.pdf;

public class obtener_letra_num {
    
    
    public static String nletra(int numero) {
        String cadena = new String();
        
        // Aqui identifico si lleva millones
        if ((numero / 1000000) > 0) {
            if ((numero / 1000000) == 1) {
                cadena = " Un Millon " + nletra(numero % 1000000);
            } else {
                cadena = nletra(numero / 1000000) + " Millones " + nletra(numero % 1000000);
            }
            
        } else // Aqui identifico si lleva Miles
        if ((numero / 1000) > 0) {
            if ((numero / 1000) == 1) {
                cadena = " Mil " + nletra(numero % 1000);
            } else {
                cadena = nletra(numero / 1000) + " Mil " + nletra(numero % 1000);
            }
            
        } else // Aqui identifico si lleva cientos
        if ((numero / 100) > 0) {
            if ((numero / 100) == 1) {
                if ((numero % 100) == 0) {
                    cadena = " Cien ";
                } else {
                    cadena = " Ciento " + nletra(numero % 100);
                }
            } else if ((numero / 100) == 5) {
                cadena = " Quinientos " + nletra(numero % 100);
            } else if ((numero / 100) == 9) {
                cadena = " Novecientos " + nletra(numero % 100);
            } else {
                cadena = nletra(numero / 100) + "cientos" + nletra(numero % 100);
            }
            
        } // Aqui se identifican las Decenas
        else if ((numero / 10) > 0) {
            switch (numero / 10) {
                case 1:
                    switch (numero % 10) {
                        case 0:
                            cadena = " Diez ";
                            break;
                        case 1:
                            cadena = " Once ";
                            break;
                        case 2:
                            cadena = " Doce ";
                            break;
                        case 3:
                            cadena = " Trece ";
                            break;
                        case 4:
                            cadena = " Catorce ";
                            break;
                        case 5:
                            cadena = " Quince ";
                            break;
                        default:
                            cadena = " Diez y " + nletra(numero % 10);
                            break;
                    }
                    break;
                case 2:
                    switch (numero % 10) {
                        case 0:
                            cadena = " Veinte ";
                            break;
                        default:
                            cadena = " Veinti" + nletra(numero % 10);
                            break;
                    }
                    break;
                case 3:
                    switch (numero % 10) {
                        case 0:
                            cadena = " Treinta ";
                            break;
                        default:
                            cadena = " Treinta y " + nletra(numero % 10);
                            break;
                    }
                    break;
                case 4:
                    switch (numero % 10) {
                        case 0:
                            cadena = " Cuarenta ";
                            break;
                        default:
                            cadena = " Cuarenta y " + nletra(numero % 10);
                            break;
                    }
                    break;
                case 5:
                    switch (numero % 10) {
                        case 0:
                            cadena = " Cincuenta ";
                            break;
                        default:
                            cadena = " Cincuenta y " + nletra(numero % 10);
                            break;
                    }
                    break;
                case 6:
                    switch (numero % 10) {
                        case 0:
                            cadena = " Sesenta ";
                            break;
                        default:
                            cadena = " Sesenta y " + nletra(numero % 10);
                            break;
                    }
                    break;
                case 7:
                    switch (numero % 10) {
                        case 0:
                            cadena = " Setenta ";
                            break;
                        default:
                            cadena = " Setenta y " + nletra(numero % 10);
                            break;
                    }
                    break;
                case 8:
                    switch (numero % 10) {
                        case 0:
                            cadena = " Ochenta ";
                            break;
                        default:
                            cadena = " Ochenta y " + nletra(numero % 10);
                            break;
                    }
                    break;
                case 9:
                    switch (numero % 10) {
                        case 0:
                            cadena = " Noventa ";
                            break;
                        default:
                            cadena = " Noventa y " + nletra(numero % 10);
                            break;
                    }
                    break;
            }
            
        } else {
            switch (numero) {
//                case 0:
//                    cadena = "Cero";
//                    break;
                case 1:
                    cadena = "Uno";
                    break;
                case 2:
                    cadena = "Dos";
                    break;
                case 3:
                    cadena = "Tres";
                    break;
                case 4:
                    cadena = "Cuatro";
                    break;
                case 5:
                    cadena = "Cinco";
                    break;
                case 6:
                    cadena = "Seis";
                    break;
                case 7:
                    cadena = "Siete";
                    break;
                case 8:
                    cadena = "Ocho";
                    break;
                case 9:
                    cadena = "Nueve";
                    break;
            }
        }
        return cadena;
    }
    
    
    
    public static String nletra_Minus(int numero) {
        String cadena = new String();
        // Aqui identifico si lleva millones
        if ((numero / 1000000) > 0) {
            if ((numero / 1000000) == 1) {
                cadena = " un millon " + nletra_Minus(numero % 1000000);
            } else {
                cadena = nletra_Minus(numero / 1000000) + " millones " + nletra_Minus(numero % 1000000);
            }
            
        } else // Aqui identifico si lleva Miles
        if ((numero / 1000) > 0) {
            if ((numero / 1000) == 1) {
                cadena = " mil " + nletra_Minus(numero % 1000);
            } else {
                cadena = nletra_Minus(numero / 1000) + " mil " + nletra_Minus(numero % 1000);
            }
            
        } else // Aqui identifico si lleva cientos
        if ((numero / 100) > 0) {
            if ((numero / 100) == 1) {
                if ((numero % 100) == 0) {
                    cadena = " cien ";
                } else {
                    cadena = " ciento " + nletra_Minus(numero % 100);
                }
            } else if ((numero / 100) == 5) {
                cadena = " quinientos " + nletra_Minus(numero % 100);
            } else if ((numero / 100) == 9) {
                cadena = " novecientos " + nletra_Minus(numero % 100);
            } else {
                cadena = nletra_Minus(numero / 100) + "cientos" + nletra_Minus(numero % 100);
            }
            
        } // Aqui se identifican las Decenas
        else if ((numero / 10) > 0) {
            switch (numero / 10) {
                case 1:
                    switch (numero % 10) {
                        case 0:
                            cadena = " diez ";
                            break;
                        case 1:
                            cadena = " once ";
                            break;
                        case 2:
                            cadena = " doce ";
                            break;
                        case 3:
                            cadena = " trece ";
                            break;
                        case 4:
                            cadena = " catorce ";
                            break;
                        case 5:
                            cadena = " quince ";
                            break;
                        default:
                            cadena = " diez y " + nletra_Minus(numero % 10);
                            break;
                    }
                    break;
                case 2:
                    switch (numero % 10) {
                        case 0:
                            cadena = " veinte ";
                            break;
                        default:
                            cadena = " veinti" + nletra_Minus(numero % 10);
                            break;
                    }
                    break;
                case 3:
                    switch (numero % 10) {
                        case 0:
                            cadena = " treinta ";
                            break;
                        default:
                            cadena = " treinta y " + nletra_Minus(numero % 10);
                            break;
                    }
                    break;
                case 4:
                    switch (numero % 10) {
                        case 0:
                            cadena = " cuarenta ";
                            break;
                        default:
                            cadena = " cuarenta y " + nletra_Minus(numero % 10);
                            break;
                    }
                    break;
                case 5:
                    switch (numero % 10) {
                        case 0:
                            cadena = " cincuenta ";
                            break;
                        default:
                            cadena = " cincuenta y " + nletra_Minus(numero % 10);
                            break;
                    }
                    break;
                case 6:
                    switch (numero % 10) {
                        case 0:
                            cadena = " sesenta ";
                            break;
                        default:
                            cadena = " sesenta y " + nletra_Minus(numero % 10);
                            break;
                    }
                    break;
                case 7:
                    switch (numero % 10) {
                        case 0:
                            cadena = " setenta ";
                            break;
                        default:
                            cadena = " setenta y " + nletra_Minus(numero % 10);
                            break;
                    }
                    break;
                case 8:
                    switch (numero % 10) {
                        case 0:
                            cadena = " ochenta ";
                            break;
                        default:
                            cadena = " ochenta y " + nletra_Minus(numero % 10);
                            break;
                    }
                    break;
                case 9:
                    switch (numero % 10) {
                        case 0:
                            cadena = " noventa ";
                            break;
                        default:
                            cadena = " noventa y " + nletra_Minus(numero % 10);
                            break;
                    }
                    break;
            }
            
        } else {
            switch (numero) {
                case 1:
                    cadena = "uno";
                    break;
                case 2:
                    cadena = "dos";
                    break;
                case 3:
                    cadena = "tres";
                    break;
                case 4:
                    cadena = "cuatro";
                    break;
                case 5:
                    cadena = "cinco";
                    break;
                case 6:
                    cadena = "seis";
                    break;
                case 7:
                    cadena = "siete";
                    break;
                case 8:
                    cadena = "ocho";
                    break;
                case 9:
                    cadena = "nueve";
                    break;
            }
        }
        return cadena;
    }
    
    
}