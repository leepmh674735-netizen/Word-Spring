<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <title>Community Roster - Aggro's Towne BBS</title>
    
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
                        <a class="nav-link nav-link-custom active" aria-current="page" href="${rosterUrl}">Roster</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-link-custom" href="${mailingUrl}">Mailing List</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-link-custom" href="${nomineeUrl}">Nominees</a>
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
    <main class="container my-5" style="max-width: 800px;">
        <div class="glass-card">
            <header class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h1 class="h3 text-white mb-1"><i class="bi bi-people-fill text-indigo me-2"></i>Community Roster</h1>
                    <p class="text-muted small mb-0">Active members of Aggro's Towne board</p>
                </div>
                <a href="${homeUrl}" class="btn btn-outline-custom btn-sm"><i class="bi bi-arrow-left me-1"></i> Back to BBS</a>
            </header>

            <c:set var="memberItems" value="${not empty members ? members : memberList}" />
            
            <c:choose>
                <c:when test="${not empty memberItems}">
                    <div class="list-group list-group-custom">
                        <c:forEach var="member" items="${memberItems}" varStatus="status">
                            <a href="member?id=${status.index}" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center py-3">
                                <div>
                                    <i class="bi bi-person-fill text-indigo me-3"></i>
                                    <span class="fw-medium"><c:out value="${member.firstName}" /> <c:out value="${member.lastName}" /></span>
                                </div>
                                <span class="badge bg-indigo bg-opacity-20 text-indigo rounded-pill px-3 py-2 small">ID #${status.index}</span>
                            </a>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert-custom-info text-center">
                        <i class="bi bi-info-circle-fill me-2 fs-5"></i> No members found in the roster database.
                    </div>
                </c:otherwise>
            </c:choose>
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