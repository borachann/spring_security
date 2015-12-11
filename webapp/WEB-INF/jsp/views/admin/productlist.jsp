<%@page import="com.kosign.wecafe.entities.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon_1.ico">
        <title>KOSIGN WECAFE - BEVERAGE STOCK</title>

		<!-- Base Css Files -->
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" />

        <!-- Font Icons -->
        <link href="${pageContext.request.contextPath}/resources/assets/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/resources/assets/ionicon/css/ionicons.min.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/resources/css/material-design-iconic-font.min.css" rel="stylesheet">

        <!-- animate css -->
        <link href="${pageContext.request.contextPath}/resources/css/animate.css" rel="stylesheet" />

        <!-- Waves-effect -->
        <link href="${pageContext.request.contextPath}/resources/css/waves-effect.css" rel="stylesheet">

        <!-- sweet alerts -->
        <link href="${pageContext.request.contextPath}/resources/assets/sweet-alert/sweet-alert.min.css" rel="stylesheet">

        <!-- Custom Files -->
        <link href="${pageContext.request.contextPath}/resources/css/helper.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css" />

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->

        <script src="${pageContext.request.contextPath}/resources/js/modernizr.min.js"></script>

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->

        <script src="${pageContext.request.contextPath}/resources/js/modernizr.min.js"></script>        
    </head>
    <body class="fixed-left-void"  ng-app="wecafe" >     
        <!-- Begin page -->
        <div id="wrapper">        
            <!-- Top Bar Start -->
			<%@ include file="topbar.jsp" %>
            <!-- Top Bar End -->

            <!-- ========== Left Sidebar Start ========== -->
            <%@ include file="left_sidebar.jsp" %>
            <!-- Left Sidebar End -->

            <!-- ============================================================== -->
            <!-- Start right Content here -->
            <!-- ============================================================== -->                      
            <div class="content-page">
                <!-- Start content -->
                <div class="content" >
                    <div class="container">
                        <!-- Page-Title -->
                        <div class="row">
                            <div class="col-sm-12">
                                <h4 class="pull-left page-title">Product List</h4>
                                <ol class="breadcrumb pull-right">
                                    <li><a href="#">KOSIGN WECAFE</a></li>
                                    <li class="active">Product List</li>
                                </ol>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <!-- <h3 class="panel-title">Product Lists</h3> -->
									    <form class="form-inline">
									        <div class="form-group">
									            <label >Search</label>
									            <input type="text" class="form-control" placeholder="Search" width="400%">
									        </div>
									    </form>
                                    </div>
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-md-12 col-sm-12 col-xs-12">
                                                <table id="datatable" class="table table-striped table-bordered">
                                                    <thead>
                                                        <tr>
                                                        	<th width="5%">#</th>
                                                            <th width="20%">Name</th>
                                                            <th style="text-align:right;" width="10%">Quantity</th>
                                                            <th style="text-align:right;" width="10%">Unit Price</th>
                                                            <th style="text-align:right;" width="10%">Cost Price</th>
                                                            <th style="text-align:right;" width="10%">Sale Price</th>
                                                            <th width="15%">Category</th>
                                                            <th style="text-align:center;" width="7%">Image</th>
                                                            <th style="text-align:center;" width="15%">Status</th>
                                                            <th style="text-align:center;" width="15%">Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="CONTENTS">
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <div class="row">
											<div class="col-md-2">
												<select id="PER_PAGE" class="form-control">
													<option value="15">15</option>
													<option value="30">30</option>
													<option value="50">50</option>
													<option value="100">100</option>
												</select>
											</div>
											<div id="PAGINATION" class="pull-right">
											</div>
										</div>
                                    </div>
                                </div>
                            </div>
                        </div> <!-- End Row -->
                    </div> <!-- container -->
                </div> <!-- content -->
                <%@ include file="footer.jsp" %>
            </div>
        </div>
        <div id="modalMessage" style="display:none; width:50%">       
	  		<div class="modal-content"> 
	  			<div class="modal-header">                                          
	            	<button type="button" class="close" aria-hidden="true"><span class="button b-close"><span>×</span></span></button>
	                <h4 class="modal-title"><strong>Message</strong></h4>
	           	</div>
	            <div class="modal-body">
	            	<h4>Do you want to delete?</h4>
	            </div>
				<div class="modal-footer">
					<div align="right">
						<button type="button" id="btnOK" class="btn btn-primary btn-flat" style="width:70px;">Ok</button>
						<button type="button" id="btnClose" class="btn btn-default btn-flat" style="width:70px;"><span class="button b-close"><span>Close</span></span></button>		
					</div>
				</div>
			</div>
		</div>
		<div id="modalMessageAlert" style="display:none; width:50%">       
	  		<div class="modal-content"> 
	  			<div class="modal-header">                                          
	            	<button type="button" class="close" aria-hidden="true"><span class="button b-close"><span>×</span></span></button>
	                <h4 class="modal-title"><strong>Message</strong></h4>
	           	</div>
	            <div class="modal-body">
	            	<h4 id="messageBody">You have been deleted successfully.</h4>
	            </div>
				<div class="modal-footer">
					<div align="right">
						<button type="button" id="btnOKAlert" class="btn btn-primary btn-flat" style="width:70px;"><span class="button b-close">Ok</span></button>
						<button type="button" id="btnCloseAlert" class="btn btn-default btn-flat" style="width:70px;"><span class="button b-close"><span>Close</span></span></button>		
					</div>
				</div>
			</div>
		</div>
        <script id="CONTENT_TEMPLATE" type="text/x-jquery-tmpl">
	    	<tr>
				<td>{{= productId}}</td>
				<td>{{= productName}}</td>
				<td style="text-align:right;">{{= quantity}}</td>
				<td style="text-align:right;">{{= unitPrice}} <span style="font-weight:bold;">Riel</span></td>
				<td style="text-align:right;">{{= costPrice}} <span style="font-weight:bold;">Riel</span>​</td>
				<td style="text-align:right;">{{= salePrice}} <span style="font-weight:bold;">Riel</span></td>
				<td>{{= category.catName}}</td>
				<td style="text-align:center;"><img style="text-align:center;" src="${pageContext.request.contextPath}/resources/images/products/{{= image}}" class="img-thumbnail" alt="" width="30px" height="30px"/></td>
				<td style="text-align:center;">
					<span>
						<a href="javascript:;" class="btn btn-success btn-sm waves-effect" type="button" id="btnStatus">{{= status}}</a>
					</span>
				</td>
				<td class="actions" style="text-align:center;">
					<a class="on-default edit-row" href="${pageContext.request.contextPath}/admin/product/{{= productId}}" id="btnUpdate" data-id="{{= productId}}"><i class="fa fa-pencil"></i></a>
					<a class="on-default remove-row" href="javascript:;" id="btnRemove" data-id="{{= productId}}"><i class="fa fa-trash-o"></i></a>
				</td>
			</tr>
        </script>
		<script>
            var resizefunc = [];
            var ctx = "${pageContext.request.contextPath}"
        </script>
        <!-- jQuery  -->
        <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/waves.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/wow.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery.nicescroll.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery.scrollTo.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/assets/jquery-detectmobile/detect.js"></script>
        <script src="${pageContext.request.contextPath}/resources/assets/fastclick/fastclick.js"></script>
        <script src="${pageContext.request.contextPath}/resources/assets/jquery-slimscroll/jquery.slimscroll.js"></script>
        <script src="${pageContext.request.contextPath}/resources/assets/jquery-blockui/jquery.blockUI.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery.tmpl.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery.bpopup.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery.bootpag.min.js"></script>

        <!-- CUSTOM JS -->
        <script src="${pageContext.request.contextPath}/resources/js/jquery.app.js"></script>
        <script>
        	$(function(){
        		var products = {};
        		var check = true;
        		
        		// TODO: FIND ALL PRODUCTS WITH CURRENT PAGE
        		products.findAllProducts = function(currentPage){
        			$.ajax({ 
    				    url: "${pageContext.request.contextPath}/api/admin/products", 
    				    type: 'GET', 
    				    data: {
   				    		"currentPage" : currentPage,
   				    		"perPage"     : $("#PER_PAGE").val(),
   				    		"startDate"   : "03/12/2015",
   				    		"endDate"     : "03/12/2015"
    				    },
    				    beforeSend: function(xhr) {
    	                    xhr.setRequestHeader("Accept", "application/json");
    	                    xhr.setRequestHeader("Content-Type", "application/json");
    	                },
    				    success: function(data) { 
							console.log(data);
							if(data.products.length>0){
								$("tbody#CONTENTS").html('');
								$("#CONTENT_TEMPLATE").tmpl(data.products).appendTo("tbody#CONTENTS");
							}else{
								$("tbody#CONTENTS").html('<tr>NO CONTENTS</tr>');
							}
					    	if(check){
					    		products.setPagination(data.pagination.totalPages,1);
					    		check=false;
					    	}
    				    },
    				    error:function(data,status,er) { 
    				        console.log("error: "+data+" status: "+status+" er:"+er);
    				    }
    				});
        		};
        		
        		// TODO: FIND PRODUCT BY ID
        		products.findProductById = function(id){
        			
        		};
        		
        		// TODO: DELETE PRODUCT BY ID
        		products.deleteProductById = function(id){
        			$.ajax({ 
    				    url: "${pageContext.request.contextPath}/admin/product/delete/"+id, 
    				    type: 'POST', 
    				    dataType: 'JSON', 
    				    beforeSend: function(xhr) {
    	                    xhr.setRequestHeader("Accept", "application/json");
    	                    xhr.setRequestHeader("Content-Type", "application/json");
    	                },
    				    success: function(data) { 
    				        if(data){
    				        	$("#modalMessageAlert").bPopup();
    				        	products.findAllProducts(1);
    				        }else{
    				        	$("#messageBody").html("You have errors when delete the product");
    				        	$("#modalMessageAlert").bPopup();
    				        }
    				    },
    				    error:function(data,status,er) { 
    				    	$("#messageBody").html("error: "+data+" status: "+status+" er:"+er);
    				    	$("#modalMessageAlert").bPopup();
    				        console.log("error: "+data+" status: "+status+" er:"+er);
    				    }
    				});
        		};
        		
        		// TODO: UPDATE PRODUCT
        		products.updateProduct = function(){
        			
        		};
        		
        		products.setPagination = function(totalPage, currentPage){
       		    	$('#PAGINATION').bootpag({
       			        total: totalPage,
       			        page: currentPage,
       			        maxVisible: 10,
       			        leaps: true,
       			        firstLastUse: true,
       			        first: 'First',
       			        last: 'Last',
       			        wrapClass: 'pagination',
       			        activeClass: 'active',
       			        disabledClass: 'disabled',
       			        nextClass: 'next',
       			        prevClass: 'prev',
       			        lastClass: 'last',
       			        firstClass: 'first'
       			    }).on("page", function(event, currentPage){
       			    	check = false;
       			    	products.findAllProducts(currentPage);
       			    }); 
        		};
        		
        		products.findAllProducts(1);
        		
        		$(document).on('click','#btnRemove',function(){
        			var id = $(this).data("id");
        			$("#modalMessage").bPopup();
        			$("#btnOK").data("id", $(this).data("id"));
        		});       		
        		
        		$("#btnOK").click(function(){
        			$("#modalMessage").bPopup().close();
        			products.deleteProductById($(this).data("id"));
        			
        		});
        		$("#PER_PAGE").change(function(){
        			check = true;
    		    	products.findAllProducts(1);
    		    });
        		
        		//
        		$("#btnClose").click(function(){
        			$("#modalMessage").bPopup().close();
        		});
        		
        		$("#btnOKAlert, #btnCloseAlert").click(function(){
        			$("#modalMessageAlert").bPopup().close();
        		});
        	});
        </script>

	</body>
</html>