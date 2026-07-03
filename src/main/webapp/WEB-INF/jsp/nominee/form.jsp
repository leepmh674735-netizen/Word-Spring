<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<c:url var="homeUrl" value="/" />
<c:url var="rosterUrl" value="/roster/list" />
<c:url var="mailingUrl" value="/mailinglist/subscribe" />
<c:url var="nomineeUrl" value="/nominees" />
<c:url var="contactUrl" value="/contact/new" />
<c:url var="registerUrl" value="/users/new" />
<c:url var="loginUrl" value="/login" />
<c:url var="logoutUrl" value="/j_spring_security_logout" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nominate a Member - Aggro's Towne BBS</title>
    
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <!-- Central Premium CSS Stylesheet -->
    <link href="<c:url value='/css/style.css' />" rel="stylesheet">
</head>
<body>

    <!-- Header Navbar -->
    <nav class="navbar navbar-expand-lg navbar-custom sticky-top">
        <div class="container">
            <a class="navbar-brand navbar-brand-custom" href="${homeUrl}">
                <i class="bi bi-terminal-fill me-2 fs-4 text-indigo"></i>
                <span>Aggro's Towne BBS</span>
            </a>
            <button class="navbar-toggler border-secondary" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon navbar-dark"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                    <li class="nav-item">
                        <a class="nav-link nav-link-custom" href="${homeUrl}">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-link-custom" href="${rosterUrl}">Roster</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-link-custom" href="${mailingUrl}">Mailing List</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-link-custom active" aria-current="page" href="${nomineeUrl}">Nominees</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-link-custom" href="${contactUrl}">Contact Us</a>
                    </li>
                </ul>
                <div class="d-flex align-items-center gap-3">
                    <security:authorize access="isAnonymous()">
                        <a href="${loginUrl}" class="btn btn-outline-custom btn-sm">Log In</a>
                        <a href="${registerUrl}" class="btn btn-premium btn-sm">Sign Up</a>
                    </security:authorize>
                    <security:authorize access="isAuthenticated()">
                        <span class="text-light me-2"><i class="bi bi-person-circle me-1 text-indigo"></i> <security:authentication property="principal.username" /></span>
                        <a href="${logoutUrl}" class="btn btn-outline-custom btn-sm">Log Out</a>
                    </security:authorize>
                </div>
            </div>
        </div>
    </nav>

    <!-- Main Container -->
    <main class="container my-5" style="max-width: 600px;">
        <div class="glass-card">
            <header class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h1 class="h3 text-white mb-1"><i class="bi bi-trophy-fill text-warning me-2"></i>Award Nomination</h1>
                    <p class="text-muted small mb-0">Nominate an outstanding member for the award</p>
                </div>
                <a href="${homeUrl}" class="btn btn-outline-custom btn-sm"><i class="bi bi-arrow-left me-1"></i> Back</a>
            </header>

            <form:form modelAttribute="member" method="post">
                <div class="mb-3">
                    <label for="firstName" class="form-label form-label-custom">First Name</label>
                    <form:input path="firstName" id="firstName" class="form-control form-control-custom" placeholder="Enter first name..." required="required" />
                    <form:errors path="firstName" class="text-danger small mt-1" />
                </div>
                
                <div class="mb-4">
                    <label for="lastName" class="form-label form-label-custom">Last Name</label>
                    <form:input path="lastName" id="lastName" class="form-control form-control-custom" placeholder="Enter last name..." required="required" />
                    <form:errors path="lastName" class="text-danger small mt-1" />
                </div>
                
                <div class="d-grid">
                    <button type="submit" class="btn btn-premium py-2.5"><i class="bi bi-check-circle-fill me-2"></i>Submit Nomination</button>
                </div>
            </form:form>
        </div>
    </main>

    <!-- Footer -->
    <footer>
        <div class="container text-center">
            <p class="mb-0">&copy; 2026 Aggro's Towne BBS. All rights reserved.</p>
        </div>
    </footer>

    <!-- Bootstrap Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>