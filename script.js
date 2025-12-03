// Mobile Navigation Toggle
const hamburger = document.getElementById('hamburger');
const navMenu = document.getElementById('navMenu');

hamburger.addEventListener('click', () => {
    navMenu.classList.toggle('active');
    
    // Animate hamburger icon
    const spans = hamburger.querySelectorAll('span');
    spans[0].style.transform = navMenu.classList.contains('active') ? 'rotate(45deg) translate(5px, 5px)' : 'none';
    spans[1].style.opacity = navMenu.classList.contains('active') ? '0' : '1';
    spans[2].style.transform = navMenu.classList.contains('active') ? 'rotate(-45deg) translate(7px, -6px)' : 'none';
});

// Close mobile menu when clicking on a link
document.querySelectorAll('.nav-link').forEach(link => {
    link.addEventListener('click', () => {
        navMenu.classList.remove('active');
        const spans = hamburger.querySelectorAll('span');
        spans[0].style.transform = 'none';
        spans[1].style.opacity = '1';
        spans[2].style.transform = 'none';
    });
});

// Smooth Scrolling for Navigation Links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            const headerOffset = 70;
            const elementPosition = target.getBoundingClientRect().top;
            const offsetPosition = elementPosition + window.pageYOffset - headerOffset;

            window.scrollTo({
                top: offsetPosition,
                behavior: 'smooth'
            });
        }
    });
});

// Imported Cars Filter
const searchImport = document.getElementById('searchImport');
const brandFilter = document.getElementById('brandFilter');
const priceFilter = document.getElementById('priceFilter');
const importedCarsGrid = document.getElementById('importedCarsGrid');

function filterImportedCars() {
    const searchTerm = searchImport.value.toLowerCase();
    const selectedBrand = brandFilter.value.toLowerCase();
    const selectedPrice = priceFilter.value;
    
    const cards = importedCarsGrid.querySelectorAll('.car-card');
    
    cards.forEach(card => {
        const brand = card.dataset.brand.toLowerCase();
        const price = parseInt(card.dataset.price);
        const title = card.querySelector('.car-title').textContent.toLowerCase();
        const specs = card.querySelector('.car-specs').textContent.toLowerCase();
        
        let matchesSearch = title.includes(searchTerm) || specs.includes(searchTerm);
        let matchesBrand = !selectedBrand || brand === selectedBrand;
        let matchesPrice = true;
        
        if (selectedPrice) {
            if (selectedPrice === '0-20000') {
                matchesPrice = price <= 20000;
            } else if (selectedPrice === '20000-40000') {
                matchesPrice = price >= 20000 && price <= 40000;
            } else if (selectedPrice === '40000-60000') {
                matchesPrice = price >= 40000 && price <= 60000;
            } else if (selectedPrice === '60000+') {
                matchesPrice = price >= 60000;
            }
        }
        
        if (matchesSearch && matchesBrand && matchesPrice) {
            card.style.display = 'block';
        } else {
            card.style.display = 'none';
        }
    });
}

searchImport.addEventListener('input', filterImportedCars);
brandFilter.addEventListener('change', filterImportedCars);
priceFilter.addEventListener('change', filterImportedCars);

// Used Cars Filter
const searchUsed = document.getElementById('searchUsed');
const yearFilter = document.getElementById('yearFilter');
const usedCarsGrid = document.getElementById('usedCarsGrid');

function filterUsedCars() {
    const searchTerm = searchUsed.value.toLowerCase();
    const selectedYear = yearFilter.value;
    
    const cards = usedCarsGrid.querySelectorAll('.car-card');
    
    cards.forEach(card => {
        const title = card.querySelector('.car-title').textContent.toLowerCase();
        const specs = card.querySelector('.car-specs').textContent.toLowerCase();
        const yearMatch = title.match(/\d{4}/);
        const carYear = yearMatch ? yearMatch[0] : '';
        
        let matchesSearch = title.includes(searchTerm) || specs.includes(searchTerm);
        let matchesYear = true;
        
        if (selectedYear) {
            if (selectedYear === 'older') {
                matchesYear = parseInt(carYear) <= 2019;
            } else {
                matchesYear = carYear === selectedYear;
            }
        }
        
        if (matchesSearch && matchesYear) {
            card.style.display = 'block';
        } else {
            card.style.display = 'none';
        }
    });
}

searchUsed.addEventListener('input', filterUsedCars);
yearFilter.addEventListener('change', filterUsedCars);

// Contact Form Submission
const contactForm = document.getElementById('contactForm');

contactForm.addEventListener('submit', function(e) {
    e.preventDefault();
    
    // Get form data
    const formData = {
        name: document.getElementById('name').value,
        email: document.getElementById('email').value,
        phone: document.getElementById('phone').value,
        inquiry: document.getElementById('inquiry').value,
        message: document.getElementById('message').value
    };
    
    // In a real application, you would send this data to a server
    console.log('Form submitted:', formData);
    
    // Show success message
    alert('Thank you for contacting ODO Motors! We will get back to you soon.');
    
    // Reset form
    contactForm.reset();
});

// Contact Buttons (Car Cards)
document.querySelectorAll('.btn-contact').forEach(button => {
    button.addEventListener('click', function() {
        const carCard = this.closest('.car-card');
        const carTitle = carCard.querySelector('.car-title').textContent;
        const carPrice = carCard.querySelector('.car-price').textContent;
        
        // Scroll to contact form
        document.getElementById('contact').scrollIntoView({ behavior: 'smooth' });
        
        // Pre-fill the message
        setTimeout(() => {
            const messageField = document.getElementById('message');
            messageField.value = `I am interested in the ${carTitle} listed at ${carPrice}. Please provide more information.`;
            
            // Set inquiry type based on car status
            const carStatus = carCard.dataset.status;
            const inquiryField = document.getElementById('inquiry');
            if (carStatus === 'new') {
                inquiryField.value = 'import';
            } else {
                inquiryField.value = 'used';
            }
        }, 500);
    });
});

// Scroll Animation for Cards
function revealOnScroll() {
    const cards = document.querySelectorAll('.car-card, .feature-card, .info-card');
    
    cards.forEach(card => {
        const cardTop = card.getBoundingClientRect().top;
        const windowHeight = window.innerHeight;
        
        if (cardTop < windowHeight - 100) {
            card.style.opacity = '1';
            card.style.transform = 'translateY(0)';
        }
    });
}

// Initialize card styles for animation
document.querySelectorAll('.car-card, .feature-card, .info-card').forEach(card => {
    card.style.opacity = '0';
    card.style.transform = 'translateY(50px)';
    card.style.transition = 'all 0.6s ease';
});

window.addEventListener('scroll', revealOnScroll);
window.addEventListener('load', revealOnScroll);

// Active Navigation Link on Scroll
window.addEventListener('scroll', () => {
    let current = '';
    const sections = document.querySelectorAll('section');
    
    sections.forEach(section => {
        const sectionTop = section.offsetTop;
        const sectionHeight = section.clientHeight;
        if (pageYOffset >= sectionTop - 150) {
            current = section.getAttribute('id');
        }
    });
    
    document.querySelectorAll('.nav-link').forEach(link => {
        link.classList.remove('active');
        if (link.getAttribute('href') === `#${current}`) {
            link.classList.add('active');
        }
    });
});

// Header Shadow on Scroll
window.addEventListener('scroll', () => {
    const header = document.querySelector('.header');
    if (window.scrollY > 0) {
        header.style.boxShadow = '0 2px 20px rgba(0,0,0,0.1)';
    } else {
        header.style.boxShadow = '0 2px 10px rgba(0,0,0,0.1)';
    }
});

// Image Loading Animation
document.querySelectorAll('.car-image img').forEach(img => {
    img.addEventListener('load', function() {
        this.style.opacity = '1';
    });
    img.style.opacity = '0';
    img.style.transition = 'opacity 0.3s ease';
});

// Add loading state for form submission
const formButton = contactForm.querySelector('button[type="submit"]');
const originalButtonText = formButton.textContent;

contactForm.addEventListener('submit', function(e) {
    formButton.textContent = 'Sending...';
    formButton.disabled = true;
    
    setTimeout(() => {
        formButton.textContent = originalButtonText;
        formButton.disabled = false;
    }, 2000);
});

// Count animation for stats
function animateValue(element, start, end, duration) {
    let startTimestamp = null;
    const step = (timestamp) => {
        if (!startTimestamp) startTimestamp = timestamp;
        const progress = Math.min((timestamp - startTimestamp) / duration, 1);
        const value = Math.floor(progress * (end - start) + start);
        element.textContent = value + (element.dataset.suffix || '');
        if (progress < 1) {
            window.requestAnimationFrame(step);
        }
    };
    window.requestAnimationFrame(step);
}

// Animate stats on scroll
let statsAnimated = false;
window.addEventListener('scroll', () => {
    const aboutSection = document.getElementById('about');
    if (aboutSection && !statsAnimated) {
        const sectionTop = aboutSection.getBoundingClientRect().top;
        const windowHeight = window.innerHeight;
        
        if (sectionTop < windowHeight - 100) {
            statsAnimated = true;
            const statElements = document.querySelectorAll('.stat h3');
            statElements[0].dataset.suffix = '+';
            statElements[1].dataset.suffix = '+';
            statElements[2].dataset.suffix = '+';
            
            animateValue(statElements[0], 0, 500, 2000);
            animateValue(statElements[1], 0, 1000, 2000);
            animateValue(statElements[2], 0, 5, 2000);
        }
    }
});

console.log('ODO Motors website loaded successfully!');
