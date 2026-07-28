<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<c:url var="homeUrl" value="/" />
<c:url var="contactUrl" value="/contact/new" />
<c:url var="rosterUrl" value="/roster/list" />
<c:url var="mailingUrl" value="/mailinglist/subscribe" />
<c:url var="nomineeUrl" value="/nominees" />
<c:url var="registerUrl" value="/users/new" />
<c:url var="loginUrl" value="/login" />
<c:url var="logoutUrl" value="/j_spring_security_logout" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Aggro's Towne BBS</title>
    
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    
    <!-- Central Premium CSS Stylesheet -->
    <link href="<c:url value='/css/style.css' />" rel="stylesheet">
</head>
<body>
    <!-- Top Rainbow Progress Bar -->
    <div class="scroll-progress-container">
        <div class="scroll-progress-bar" id="scrollProgress"></div>
    </div>

    <!-- Floating Rainbow Bubbles Background -->
    <div class="floating-bubbles" id="bubbleContainer"></div>

    <!-- Header Navbar -->
    <nav class="navbar navbar-expand-lg navbar-custom sticky-top">
        <div class="container">
            <a class="navbar-brand navbar-brand-custom" href="${homeUrl}">
                <i class="bi bi-paw-fill me-2 fs-4 text-indigo"></i>
                <span>Aggro's Towne BBS 🐾</span>
            </a>
            <button class="navbar-toggler border-secondary" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon navbar-dark"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                    <li class="nav-item">
                        <a class="nav-link nav-link-custom active" aria-current="page" href="${homeUrl}">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-link-custom" href="${rosterUrl}">Roster</a>
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
    <main class="container my-5">
        
        <!-- Cute Disney Character Hero Section -->
        <header class="hero-section text-start">
            <div class="row align-items-center g-4">
                <!-- Text contents -->
                <div class="col-lg-8">
                    <span class="badge bg-success bg-opacity-10 text-success border border-success border-opacity-25 px-3 py-2 rounded-pill mb-3">
                        <span class="pulse-dot me-2"></span>SYSOP ONLINE
                    </span>
                    <h1 class="hero-title display-4 fw-bold mb-3"><span class="rainbow-text">Welcome to the BBS Hub 🌈</span></h1>
                    <p class="hero-subtitle fs-5 mb-4 opacity-90">
                        The digital meeting ground for sharing insights, announcements, roster lists, and communicating directly with our community. Let's build a magical digital space together! ✨⭐
                    </p>
                    <div class="d-flex justify-content-start gap-3">
                        <a href="${rosterUrl}" class="btn btn-premium px-4 py-2">Explore Roster 🎨</a>
                        <a href="${contactUrl}" class="btn btn-outline-custom px-4 py-2">Send Message 💌</a>
                    </div>
                </div>
                <!-- Cute Mascot column -->
                <div class="col-lg-4 text-center">
                    <div class="d-flex align-items-center justify-content-center gap-3 mascot-container">
                        <img src="<c:url value='/images/cyber_cat_mascot.png' />" alt="Cyber Neko Mascot" class="mascot-img img-fluid" style="max-height: 180px; width: auto;" />
                        <div class="speech-bubble text-start">
                            <span class="badge badge-rainbow-cat mb-1">Guide Neko 🐱</span>
                            <p class="mb-0 fw-medium small text-muted">Welcome, Sparkle~! I am Cyber-Neko. Explore our magical roster or drop a friendly message! 🌈✨🐾🐱</p>
                        </div>
                    </div>
                </div>
            </div>
        </header>

        <!-- Main Content Row -->
        <div class="row g-4">
            
            <!-- Left Side: Announcements & MOTD -->
            <div class="col-lg-8">
                
                <!-- MOTD Card -->
                <article class="glass-card motd-card cat-ears-card mb-4">
                    <div class="cat-ears-inner"></div>
                    <div class="d-flex align-items-center mb-3" style="position: relative; z-index: 12;">
                        <div class="service-icon bg-indigo text-white me-3" style="width: 40px; height: 40px; border-radius: 8px;">
                            <i class="bi bi-megaphone-fill"></i>
                        </div>
                        <h2 class="h4 mb-0 text-white">Message of the Day</h2>
                    </div>
                    <div class="card-body-text fs-5 text-light opacity-90">
                        <c:choose>
                            <c:when test="${not empty motd}">
                                <c:out value="${motd.htmlText}" escapeXml="false" />
                            </c:when>
                            <c:otherwise>
                                <p class="text-muted italic mb-0">[System broadcast message is currently unavailable]</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </article>

                <!-- Important Messages Card list -->
                <section class="glass-card cat-ears-card">
                    <div class="cat-ears-inner"></div>
                    <div class="d-flex align-items-center mb-4" style="position: relative; z-index: 12;">
                        <div class="service-icon bg-warning text-dark me-3" style="width: 40px; height: 40px; border-radius: 8px; background: #fbbf24;">
                            <i class="bi bi-exclamation-triangle-fill"></i>
                        </div>
                        <h2 class="h4 mb-0 text-white">Important Announcements</h2>
                    </div>

                    <c:choose>
                        <c:when test="${importantMessages != null}"> 
                            <c:forEach var="message" items="${importantMessages}">
                                <div class="important-card">
                                    <c:out value="${message.htmlText}" escapeXml="false" />
                                </div>
                            </c:forEach> 
                        </c:when>
                        <c:otherwise>
                            <div class="p-4 rounded border border-warning border-opacity-10 bg-warning bg-opacity-5 text-center text-warning">
                                <i class="bi bi-info-circle-fill me-2 fs-5"></i> Important announcements are currently offline.
                            </div>
                        </c:otherwise>
                    </c:choose>
                </section>
                
            </div>

            <!-- Right Side: Services & Quick Links -->
            <aside class="col-lg-4">
                
                <!-- Quick Navigation Services -->
                <div class="glass-card">
                    <h3 class="h5 text-white mb-4"><i class="bi bi-grid-fill me-2 text-indigo"></i>Quick Directory</h3>
                    
                    <a href="${rosterUrl}" class="service-item">
                        <div class="service-icon">
                            <i class="bi bi-people-fill"></i>
                        </div>
                        <div>
                            <div class="fw-semibold">Community Roster</div>
                            <small class="text-muted text-wrap">View the list of members and award winners</small>
                        </div>
                    </a>

                    <a href="${mailingUrl}" class="service-item">
                        <div class="service-icon">
                            <i class="bi bi-envelope-check-fill"></i>
                        </div>
                        <div>
                            <div class="fw-semibold">Mailing List</div>
                            <small class="text-muted text-wrap">Subscribe for newsletter broadcasts</small>
                        </div>
                    </a>

                    <a href="${nomineeUrl}" class="service-item">
                        <div class="service-icon">
                            <i class="bi bi-trophy-fill"></i>
                        </div>
                        <div>
                            <div class="fw-semibold">Nomination Portal</div>
                            <small class="text-muted text-wrap">Nominate a member for the annual award</small>
                        </div>
                    </a>

                    <a href="${contactUrl}" class="service-item">
                        <div class="service-icon">
                            <i class="bi bi-envelope-fill"></i>
                        </div>
                        <div>
                            <div class="fw-semibold">Contact Sysop</div>
                            <small class="text-muted text-wrap">Send a message to server administrators</small>
                        </div>
                    </a>
                </div>

                <!-- Server Info widget -->
                <div class="glass-card mt-4">
                    <h3 class="h5 text-white mb-3"><i class="bi bi-cpu-fill me-2 text-indigo"></i>BBS Metadata</h3>
                    <ul class="list-unstyled mb-0 text-muted small">
                        <li class="d-flex justify-content-between mb-2">
                            <span>Protocol:</span>
                            <span class="text-white fw-medium">HTTP / Spring MVC</span>
                        </li>
                        <li class="d-flex justify-content-between mb-2">
                            <span>Security:</span>
                            <span class="text-white fw-medium">Spring Security</span>
                        </li>
                        <li class="d-flex justify-content-between mb-2">
                            <span>Database:</span>
                            <span class="text-white fw-medium">PostgreSQL (Supabase)</span>
                        </li>
                        <li class="d-flex justify-content-between">
                            <span>Framework:</span>
                            <span class="text-white fw-medium">Spring Boot 3.4.3</span>
                        </li>
                    </ul>
                </div>
                
            </aside>
            
        </div>
    </main>

    <!-- Footer -->
    <footer>
        <div class="container text-center">
            <p class="mb-2">&copy; 2026 Aggro's Towne BBS. All rights reserved.</p>
            <p class="mb-0 text-muted small">Powered by Spring Boot, Jakarta Server Pages, and Bootstrap 5.</p>
        </div>
    </footer>

    <!-- Bootstrap Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    
    <!-- 🌈 Premium Rainbow & Cute Interaction Script -->
    <script>
        // Scroll Progress Indicator
        window.addEventListener('scroll', () => {
            const winScroll = document.documentElement.scrollTop || document.body.scrollTop;
            const height = document.documentElement.scrollHeight - document.documentElement.clientHeight;
            const scrolled = height > 0 ? (winScroll / height) * 100 : 0;
            const bar = document.getElementById('scrollProgress');
            if (bar) {
                bar.style.width = scrolled + '%';
            }
        });

        // Floating Bubbles Generator
        const bubbleContainer = document.getElementById('bubbleContainer');
        const createBubble = () => {
            if (!bubbleContainer) return;
            const bubble = document.createElement('div');
            bubble.classList.add('bubble');
            
            // Random size, position, and speed
            const size = Math.random() * 20 + 15; // 15px to 35px
            bubble.style.width = size + 'px';
            bubble.style.height = size + 'px';
            bubble.style.left = (Math.random() * 100) + 'vw';
            
            const duration = Math.random() * 8 + 12; // 12s to 20s
            bubble.style.animationDuration = duration + 's';
            
            bubbleContainer.appendChild(bubble);
            
            // Remove bubble after animation ends
            setTimeout(() => {
                bubble.remove();
            }, duration * 1000);
        };
        // Initial bubbles
        for (let i = 0; i < 6; i++) {
            setTimeout(createBubble, Math.random() * 4000);
        }
        setInterval(createBubble, 3000);

        // Interactive Mouse Rainbow Sparkle Trail
        const symbols = ['🐾', '💖', '⭐', '✨', '🌈', '🐱'];
        const colors = ['#ff9aa2', '#ffb7b2', '#ffd97d', '#b5e2a2', '#a0c4ff', '#c7ceea'];
        let lastX = 0;
        let lastY = 0;
        const minMoveDist = 15; // Minimum movement distance to spawn a particle

        window.addEventListener('mousemove', (e) => {
            const dist = Math.hypot(e.clientX - lastX, e.clientY - lastY);
            if (dist < minMoveDist) return;
            
            lastX = e.clientX;
            lastY = e.clientY;
            
            createSparkle(e.clientX, e.clientY);
        });

        function createSparkle(x, y) {
            const sparkle = document.createElement('span');
            sparkle.classList.add('rainbow-sparkle');
            
            // Random character and color
            sparkle.textContent = symbols[Math.floor(Math.random() * symbols.length)];
            sparkle.style.color = colors[Math.floor(Math.random() * colors.length)];
            
            // Position fixed matching clientX/Y
            sparkle.style.left = x + 'px';
            sparkle.style.top = y + 'px';
            
            document.body.appendChild(sparkle);
            
            // Remove after animation finishes (1000ms)
            setTimeout(() => {
                sparkle.remove();
            }, 1000);
        }
    </script>
</body>
</html>