
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%
    /*
        OBSERVACION: LINK: https://www.youtube.com/watch?v=OJEQaVT45XA 
    */
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Prueba Menu Web</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <link href="${pageContext.request.contextPath}/recursos/css/style.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/recursos/css/styleEditBootstrap.css" rel="stylesheet" type="text/css"/>

<!--        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/solid.css">
        <link href="${pageContext.request.contextPath}/recursos/fontawesome-free-5.10.1-web/css/solid.css" rel="stylesheet" type="text/css"/>
        <script src="${pageContext.request.contextPath}/recursos/fontawesome-free-5.10.1-web/js/all.js" type="text/javascript"></script>-->
        
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenu_backup.css">
        
    </head>

    <body>
        <%
//        <div class="container">
//            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
//                <a class="navbar-brand text-white">
//                    <!--<img src="logo.png" height="40px" />-->
//                    PRUEBA
//                </a>
//                <button class="navbar-toggler" data-target="menu" data-toggle="collapse">
//                    <span class="navbar-toggler-icon"></span>
//                </button>
//                <div class="collapse navbar-collapse" id="menu">
//                    <ul class="navbar-nav mr-auto">
//                        <li class="nav-item active">
//                            <a href="#" class="nav-link">Inicio</a>
//                        </li>
//                        <li class="nav-item active">
//                            <a href="#" class="nav-link">Posts</a>
//                        </li>
//                        <li class="nav-item dropdown">
//                            <a class="nav-link dropdown-toggle active" data-toggle="dropdown" data-target="desplegable" href="#">
//                                Adminitracion
//                            </a>
//                            <div class="dropdown-menu">
//                                <a class="dropdown-item" href="#">Item 1</a>
//                                <a class="dropdown-item" href="#">Item 2</a>
//                            </div>
//                        </li>
//                    </ul>
//                    <span class="navbar-text">
//                        Este es un texto plano
//                    </span>
//                </div>
//            </nav>
//        </div>
        %>
        
        
        <input type="checkbox" id="nav-toggle">
        
        <div class="sidebar">
            <div class="sidebar-brand">
                <h2><span class="lab la-accusoft"></span> <span>Accusoft</span></h2>
            </div>
            
            <div class="sidebar-menu">
                <ul>
                    <li>
                        <a href="" class="active"><span class="las la-igloo"></span>
                        <span>Dashboard</span></a>
                    </li>
                    <li>
                        <a href=""><span class="las la-users"></span>
                        <span>Customers</span></a>
                    </li>
                    <li>
                        <a href=""><span class="las la-clipboard-list"></span>
                        <span>Projects</span></a>
                    </li>
                    <li>
                        <a href=""><span class="las la-shopping-bag"></span>
                        <span>Orders</span></a>
                    </li>
                    <li>
                        <a href=""><span class="las la-receipt"></span>
                        <span>Inventory</span></a>
                    </li>
                    <li>
                        <a href=""><span class="las la-user-circle"></span>
                        <span>Accounts</span></a>
                    </li>
                    <li>
                        <a href=""><span class="las la-user-clipboard-list"></span>
                        <span>Tasks</span></a>
                    </li>
                </ul>
            </div>
        </div>
        
        
        <div class="main-content">
            <header>
                <h2>
                    <label for="nav-toggle">
                        <span class="las la-bars"></span>
                    </label>
                    
                    Dashboard
                </h2>
                
                <div class="search-wrapper">
                    <span class="las la-search"></span>
                    <input type="search" placeholder="Search here" />
                </div>
                
                <div class="user-wrapper">
                    <img src="${pageContext.request.contextPath}/recursos/img/avatar.png" width="40px" height="40px" alt="">
                    <div>
                        <h4>John Doe</h4>
                        <small>Super admin</small>
                    </div>
                </div>
            </header>
            
            <main>
                <div class="cards">
                    <div class="card-single">
                        <div>
                            <h1>54</h1>
                            <span>Customers</span>
                        </div>
                        <div>
                            <span class="las la-users"></span>
                        </div>
                    </div>
                    
                    <div class="card-single">
                        <div>
                            <h1>79</h1>
                            <span>Projects</span>
                        </div>
                        <div>
                            <span class="las la-clipboard-list"></span>
                        </div>
                    </div>
                    
                    <div class="card-single">
                        <div>
                            <h1>124</h1>
                            <span>Orders</span>
                        </div>
                        <div>
                            <span class="las la-shopping-bag"></span>
                        </div>
                    </div>
                    
                    <div class="card-single">
                        <div>
                            <h1>$6k</h1>
                            <span>Income</span>
                        </div>
                        <div>
                            <span class="lab la-google-wallet"></span>
                        </div>
                    </div>
                </div>
                
                <div class="recent-grid">
                    <div class="projects">
                        <div class="card">
                            <div class="card-header">
                                <h3>Recent Projects</h3>
                                
                                <button>See all <span class="las la-arrow-right"></span></button>
                            </div>
                            
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table width="100%">
                                        <thead>
                                            <tr>
                                                <td>Project Title</td>
                                                <td>Department</td>
                                                <td>Status</td>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>UI/UX Design</td>
                                                <td>UI Team</td>
                                                <td>
                                                    <span class="status purple"></span>
                                                    review
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Web development</td>
                                                <td>Frontend</td>
                                                <td>
                                                    <span class="status pink"></span>
                                                    in progress
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Ushop app</td>
                                                <td>Mobile Team</td>
                                                <td>
                                                    <span class="status orange"></span>
                                                    pending
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>UI/UX Design</td>
                                                <td>UI Team</td>
                                                <td>
                                                    <span class="status purple"></span>
                                                    review
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Web development</td>
                                                <td>Frontend</td>
                                                <td>
                                                    <span class="status pink"></span>
                                                    in progress
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Ushop App</td>
                                                <td>Mobile Team</td>
                                                <td>
                                                    <span class="status orange"></span>
                                                    pending
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>UI/UX Design</td>
                                                <td>UI Team</td>
                                                <td>
                                                    <span class="status purple"></span>
                                                    review
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Web development</td>
                                                <td>Frontend</td>
                                                <td>
                                                    <span class="status pink"></span>
                                                    in progress
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Ushop App</td>
                                                <td>Mobile Team</td>
                                                <td>
                                                    <span class="status orange"></span>
                                                    pending
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>UI/UX Design</td>
                                                <td>UI Team</td>
                                                <td>
                                                    <span class="status purple"></span>
                                                    review
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Web development</td>
                                                <td>Frontend</td>
                                                <td>
                                                    <span class="status pink"></span>
                                                    in progress
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Ushop App</td>
                                                <td>Mobile Team</td>
                                                <td>
                                                    <span class="status orange"></span>
                                                    pending
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="customers">
                        <div class="card">
                            <div class="card-header">
                                <h3>New customer</h3>
                                
                                <button>See all <span class="las la-arrow-right"></span></button>
                            </div>
                            
                            <div class="card-body">
                                <div class="customers">
                                    <div class="info">
                                        <img src="${pageContext.request.contextPath}/recursos/img/avatar.png" width="40px" height="40px" alt="">
                                        <div>
                                            <h4>Lewis S. Cunningham</h4>
                                            <small>CEO Excerpt</small>
                                        </div>
                                    </div>
                                    <div class="contact">
                                        <span class="las la-user-circle"></span>
                                        <span class="las la-comment"></span>
                                        <span class="las la-phone"></span>
                                    </div>
                                </div>
                                
                                <div class="customers">
                                    <div class="info">
                                        <img src="${pageContext.request.contextPath}/recursos/img/avatar.png" width="40px" height="40px" alt="">
                                        <div>
                                            <h4>Lewis S. Cunningham</h4>
                                            <small>CEO Excerpt</small>
                                        </div>
                                    </div>
                                    <div class="contact">
                                        <span class="las la-user-circle"></span>
                                        <span class="las la-comment"></span>
                                        <span class="las la-phone"></span>
                                    </div>
                                </div>
                                
                                <div class="customers">
                                    <div class="info">
                                        <img src="${pageContext.request.contextPath}/recursos/img/avatar.png" width="40px" height="40px" alt="">
                                        <div>
                                            <h4>Lewis S. Cunningham</h4>
                                            <small>CEO Excerpt</small>
                                        </div>
                                    </div>
                                    <div class="contact">
                                        <span class="las la-user-circle"></span>
                                        <span class="las la-comment"></span>
                                        <span class="las la-phone"></span>
                                    </div>
                                </div>
                                
                                <div class="customers">
                                    <div class="info">
                                        <img src="${pageContext.request.contextPath}/recursos/img/avatar.png" width="40px" height="40px" alt="">
                                        <div>
                                            <h4>Lewis S. Cunningham</h4>
                                            <small>CEO Excerpt</small>
                                        </div>
                                    </div>
                                    <div class="contact">
                                        <span class="las la-user-circle"></span>
                                        <span class="las la-comment"></span>
                                        <span class="las la-phone"></span>
                                    </div>
                                </div>
                                
                                <div class="customers">
                                    <div class="info">
                                        <img src="${pageContext.request.contextPath}/recursos/img/avatar.png" width="40px" height="40px" alt="">
                                        <div>
                                            <h4>Lewis S. Cunningham</h4>
                                            <small>CEO Excerpt</small>
                                        </div>
                                    </div>
                                    <div class="contact">
                                        <span class="las la-user-circle"></span>
                                        <span class="las la-comment"></span>
                                        <span class="las la-phone"></span>
                                    </div>
                                </div>
                                
                                <div class="customers">
                                    <div class="info">
                                        <img src="${pageContext.request.contextPath}/recursos/img/avatar.png" width="40px" height="40px" alt="">
                                        <div>
                                            <h4>Lewis S. Cunningham</h4>
                                            <small>CEO Excerpt</small>
                                        </div>
                                    </div>
                                    <div class="contact">
                                        <span class="las la-user-circle"></span>
                                        <span class="las la-comment"></span>
                                        <span class="las la-phone"></span>
                                    </div>
                                </div>
                                
                                <div class="customers">
                                    <div class="info">
                                        <img src="${pageContext.request.contextPath}/recursos/img/avatar.png" width="40px" height="40px" alt="">
                                        <div>
                                            <h4>Lewis S. Cunningham</h4>
                                            <small>CEO Excerpt</small>
                                        </div>
                                    </div>
                                    <div class="contact">
                                        <span class="las la-user-circle"></span>
                                        <span class="las la-comment"></span>
                                        <span class="las la-phone"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
            </main>
        </div>
        
        
        <%
//        <p>Hello! This is the default welcome page for a Spring Web MVC project.</p>
//        
//            String user="PRUEBA";
//            try {
//                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//                String connectionURL = "jdbc:sqlserver://DESKTOP-M9F7GB3:1433;databaseName=sys_usuario;user=usuarioSQL;password=admin;";
////                String connectionURL = "jdbc:sqlserver://192.168.1.11:1433;databaseName=sys_usuario;user=usuarioSQL;password=admin;";
//                Connection con = DriverManager.getConnection(connectionURL);
//                System.out.println("Nos conectamos");
//                
//                Statement st = con.createStatement();
//                ResultSet rs = st.executeQuery("SELECT * FROM sys_usuario");
//                
//                while(rs.next()) {
//                    int ID = rs.getInt(1);
//                    String USUARIO = rs.getString(2);
//                    String CLAVE = rs.getString(3);
//                    int IDALUM = rs.getInt(4);
//                    String ESTADO = rs.getString(5);
//                    System.out.println("--------------------");
//                    System.out.println("ID_USUARIO:   "+ID);
//                    System.out.println("USUARIO:      "+USUARIO);
//                    System.out.println("CLAVE:        "+CLAVE);
//                    System.out.println("ID_ALUMNO:    "+IDALUM);
//                    System.out.println("ESTADO:       "+ESTADO);
//                    System.out.println("--------------------");
//                    user = USUARIO;
//                }
//            } catch (SQLException e) {
//                user = "error base";
//                System.out.println("_1__ERROR:      "+e.getMessage());
//                System.out.println("_2__ERROR:      "+e.getErrorCode());
//                System.out.println("_3__ERROR:      "+e.getNextException());
//    //            Logger.getLogger(InicioSesionController.class.getName()).log(Level.SEVERE, null, e);
//            }
//        
//        <p>
//            User 0:    <%= user >
//        </p>
        %>
        
        <form action="CSe" method="post">
        <table border="1">
            <tr>
                <td>CÓDIGO:</td>
                <td>
                    <input type="text" value="" name="" class="form-control" />
                </td>
            </tr>
            <tr>
                <td>Nombres:</td>
                <td>
                    <input type="text" value="" name="" class="form-control" />
                </td>
            </tr>
            <tr>
                <td>Apellidos:</td>
                <td>
                    <input type="text" value="" name="" class="form-control" />
                </td>
            </tr>
            <tr>
                <td>Nro. Cédula:</td>
                <td>
                    <input type="text" value="" name="" class="form-control" />
                </td>
            </tr>
            <tr>
                <td>Teléfono:</td>
                <td>
                    <input type="text" value="" name="" class="form-control" />
                </td>
            </tr>
            <tr>
                <td>Dirección:</td>
                <td>
                    <input type="text" value="" name="" class="form-control" />
                </td>
            </tr>
            <tr>
                <td>Email:</td>
                <td>
                    <input type="text" value="" name="" class="form-control" />
                </td>
            </tr>
            <tr>
                <td>Login:</td>
                <td>
                    <input type="text" value="" name="" class="form-control" />
                </td>
            </tr>
            <tr>
                <td>Contraseña:</td>
                <td>
                    <input type="text" value="" name="" class="form-control" />
                </td>
            </tr>
            <tr>
                <td>Estado:</td>
                <td>
                    <select class="form-control">
                        <option value="">ACTIVO</option>
                        <option value="">INACTIVO</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" name="accion" value="GRABAR" class="btn btn-primary" />
                    <input type="submit" name="accion" value="CANCELAR" class="btn btn-primary" />
                    <input type="submit" name="accion" value="SALIR" class="btn btn-primary" />
                </td>
            </tr>
        </table>
        </form>
        
        
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
        
    </body>
</html>