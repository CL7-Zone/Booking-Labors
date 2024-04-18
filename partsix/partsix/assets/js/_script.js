/*
  Template Name: Partsix - Auto Parts & Car Accessories Shop HTML Template
  Author Name: Hook theme
  Author URL: https://themeforest.net/user/hooktheme
  Version: 1.0.0


  ----------------------------
  [Table of contents CSS] 
  ----------------------------
  
  1. slider swiper activation
  2. product swiper column4 activation
  3. product swiper column4 style2 activation
  4. single product nav activation
  5. product swiper column1 activation
  6. blog swiper activation
  7. testimonial swiper activation
  8. quickview swiper activation
  9. product details media swiper activation
  10. testimonial active one activation
  11. tab activation
  12. countdown activation
  13. active class remove class activation
  14. OffCanvas Sidebar Activation
  15. Qunatity Button Activation
  16. Modal JS Activation
  17. Accordion Activation
  18. Footer widget Activation
  19. Flightbox Activation
  20. CounterUp Activation
  21. Offcanvas Mobile Menu Function
  22. Newsletter popup Activation

*/

"use strict";

// Preloader
const preLoader = function () {
  let preloaderWrapper = document.getElementById("preloader");
  window.onload = () => {
    preloaderWrapper.classList.add("loaded");
  };
};
preLoader();

// getSiblings
var getSiblings = function (elem) {
  const siblings = [];
  let sibling = elem.parentNode.firstChild;
  while (sibling) {
    if (sibling.nodeType === 1 && sibling !== elem) {
      siblings.push(sibling);
    }
    sibling = sibling.nextSibling;
  }
  return siblings;
};

/* Slide Up */
var slideUp = (target, time) => {
  const duration = time ? time : 500;
  target.style.transitionProperty = "height, margin, padding";
  target.style.transitionDuration = duration + "ms";
  target.style.boxSizing = "border-box";
  target.style.height = target.offsetHeight + "px";
  target.offsetHeight;
  target.style.overflow = "hidden";
  target.style.height = 0;
  window.setTimeout(() => {
    target.style.display = "none";
    target.style.removeProperty("height");
    target.style.removeProperty("overflow");
    target.style.removeProperty("transition-duration");
    target.style.removeProperty("transition-property");
  }, duration);
};

/* Slide Down */
var slideDown = (target, time) => {
  const duration = time ? time : 500;
  target.style.removeProperty("display");
  let display = window.getComputedStyle(target).display;
  if (display === "none") display = "block";
  target.style.display = display;
  const height = target.offsetHeight;
  target.style.overflow = "hidden";
  target.style.height = 0;
  target.offsetHeight;
  target.style.boxSizing = "border-box";
  target.style.transitionProperty = "height, margin, padding";
  target.style.transitionDuration = duration + "ms";
  target.style.height = height + "px";
  window.setTimeout(() => {
    target.style.removeProperty("height");
    target.style.removeProperty("overflow");
    target.style.removeProperty("transition-duration");
    target.style.removeProperty("transition-property");
  }, duration);
};

// Get window top offset
function TopOffset(el) {
  let rect = el.getBoundingClientRect(),
    scrollTop = window.pageYOffset || document.documentElement.scrollTop;
  return { top: rect.top + scrollTop };
}

// Header sticky activation
const headerStickyWrapper = document.querySelector("header");
const headerStickyTarget = document.querySelector(".header__sticky");

if (headerStickyTarget) {
  let headerHeight = headerStickyWrapper.clientHeight;
  window.addEventListener("scroll", function () {
    let StickyTargetElement = TopOffset(headerStickyWrapper);
    let TargetElementTopOffset = StickyTargetElement.top;

    if (window.scrollY > TargetElementTopOffset) {
      headerStickyTarget.classList.add("sticky");
    } else {
      headerStickyTarget.classList.remove("sticky");
    }
  });
}

// Scroll up activation
const scrollTop = document.getElementById("scroll__top");
if (scrollTop) {
  scrollTop.addEventListener("click", function () {
    window.scroll({ top: 0, left: 0, behavior: "smooth" });
  });
  window.addEventListener("scroll", function () {
    if (window.scrollY > 300) {
      scrollTop.classList.add("active");
    } else {
      scrollTop.classList.remove("active");
    }
  });
}

/*
  1. slider swiper activation
*/
var swiper = new Swiper(".hero__slider--activation", {
  slidesPerView: 1,
  loop: true,
  clickable: true,
  speed: 500,
  spaceBetween: 30,
  navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev",
  },
  pagination: {
    el: ".swiper-pagination",
    clickable: true,
  },
});

/*
  2. product swiper column4 activation
*/
var swiper = new Swiper(".product__swiper--activation", {
  slidesPerView: 4,
  loop: true,
  clickable: true,
  spaceBetween: 30,
  breakpoints: {
    992: {
      slidesPerView: 4,
    },
    768: {
      slidesPerView: 3,
      spaceBetween: 30,
    },
    480: {
      slidesPerView: 2,
      spaceBetween: 20,
    },
    0: {
      slidesPerView: 1,
    },
  },
  navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev",
  },
});

/*
  3. product swiper column4 style2 activation
*/
var swiper = new Swiper(".product__swiper--column4__style2", {
  slidesPerView: 4,
  loop: true,
  clickable: true,
  spaceBetween: 30,
  breakpoints: {
    1366: {
      slidesPerView: 4,
    },
    1200: {
      slidesPerView: 3,
    },
    992: {
      slidesPerView: 2,
    },
    768: {
      slidesPerView: 3,
      spaceBetween: 30,
    },
    480: {
      slidesPerView: 2,
      spaceBetween: 20,
    },
    0: {
      slidesPerView: 1,
    },
  },
  navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev",
  },
});

/*
  4. single product nav activation
*/
var swiper = new Swiper(".single__product--nav", {
  loop: true,
  spaceBetween: 20,
  slidesPerView: 5,
  freeMode: true,
  watchSlidesProgress: true,
  breakpoints: {
    992: {
      spaceBetween: 20,
    },
    768: {
      slidesPerView: 5,
      spaceBetween: 15,
    },
    480: {
      slidesPerView: 4,
    },
    320: {
      slidesPerView: 3,
    },
    200: {
      slidesPerView: 2,
    },
    0: {
      slidesPerView: 1,
    },
  },
  navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev",
  },
});
var swiper2 = new Swiper(".single__product--preview", {
  loop: true,
  spaceBetween: 10,
  thumbs: {
    swiper: swiper,
  },
});

/*
  5. product swiper column1 activation
*/
var swiper = new Swiper(".product__swiper--column1", {
  slidesPerView: 4,
  loop: false,
  clickable: true,
  spaceBetween: 15,
  direction: "vertical",
  navigation: {
    nextEl: ".small__product .swiper-button-next",
    prevEl: ".small__product .swiper-button-prev",
  },
});

/*
  6. blog swiper activation
*/
var swiper = new Swiper(".blog__swiper--activation", {
  slidesPerView: 3,
  loop: true,
  clickable: true,
  spaceBetween: 30,
  breakpoints: {
    992: {
      slidesPerView: 3,
    },
    768: {
      slidesPerView: 2,
      spaceBetween: 30,
    },
    560: {
      slidesPerView: 2,
      spaceBetween: 20,
    },
    0: {
      slidesPerView: 1,
    },
  },
  navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev",
  },
});

/*
  7. testimonial swiper activation
*/
var swiper = new Swiper(".testimonial__swiper--activation", {
  slidesPerView: 2,
  loop: true,
  clickable: true,
  spaceBetween: 30,
  breakpoints: {
    576: {
      slidesPerView: 2,
      spaceBetween: 20,
    },

    0: {
      slidesPerView: 1,
    },
  },
  navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev",
  },
});

/*
  8. quickview swiper activation
*/
var swiper = new Swiper(".quickview__swiper--activation", {
  slidesPerView: 1,
  loop: true,
  clickable: true,
  spaceBetween: 30,
  navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev",
  },
  pagination: {
    el: ".swiper-pagination",
    clickable: true,
  },
});

/*
  9. product details media swiper activation
*/
var swiper = new Swiper(".product__media--nav", {
  loop: true,
  spaceBetween: 10,
  slidesPerView: 4,
  freeMode: true,
  watchSlidesProgress: true,
  breakpoints: {
    480: {
      slidesPerView: 4,
    },
    200: {
      slidesPerView: 3,
    },
    0: {
      slidesPerView: 1,
    },
  },
  navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev",
  },
});
var swiper2 = new Swiper(".product__media--preview", {
  loop: true,
  spaceBetween: 10,
  thumbs: {
    swiper: swiper,
  },
});

/*
  10. testimonial active one activation
*/
var swiper3 = new Swiper(".testimonial__active--one", {
  loop: true,
  spaceBetween: 20,
  slidesPerView: 5,
  centeredSlides: true,
  freeMode: true,
  watchSlidesProgress: true,
  breakpoints: {
    768: {
      slidesPerView: 5,
    },
    576: {
      slidesPerView: 3,
    },
    200: {
      slidesPerView: 3,
    },
    0: {
      slidesPerView: 1,
    },
  },
  navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev",
  },
});
var swiper2 = new Swiper(".testimonial__active--two", {
  loop: true,
  spaceBetween: 10,
  thumbs: {
    swiper: swiper3,
  },
});

/*
  11. tab activation
*/
const tab = function (wrapper) {
  let tabContainer = document.querySelector(wrapper);
  if (tabContainer) {
    tabContainer.addEventListener("click", function (evt) {
      let listItem = evt.target;
      if (listItem.hasAttribute("data-toggle")) {
        let targetId = listItem.dataset.target,
          targetItem = document.querySelector(targetId);
        listItem.parentElement
          .querySelectorAll('[data-toggle="tab"]')
          .forEach(function (list) {
            list.classList.remove("active");
          });
        listItem.classList.add("active");
        targetItem.classList.add("active");
        setTimeout(function () {
          targetItem.classList.add("show");
        }, 150);
        getSiblings(targetItem).forEach(function (pane) {
          pane.classList.remove("show");
          setTimeout(function () {
            pane.classList.remove("active");
          }, 150);
        });
      }
    });
  }
};

// Homepage 1 product tab
tab(".product__tab--one");

/*
  12. countdown activation
*/
document.querySelectorAll("[data-countdown]").forEach(function (elem) {
  const countDownItem = function (value, label) {
    return `<div class="countdown__item" ${label}"><span class="countdown__number">${value}</span><p class="countdown__text">${label}</p></div>`;
  };
  const date = new Date(elem.getAttribute("data-countdown")).getTime(),
    second = 1000,
    minute = second * 60,
    hour = minute * 60,
    day = hour * 24;
  const countDownInterval = setInterval(function () {
    let currentTime = new Date().getTime(),
      timeDistance = date - currentTime,
      daysValue = Math.floor(timeDistance / day),
      hoursValue = Math.floor((timeDistance % day) / hour),
      minutesValue = Math.floor((timeDistance % hour) / minute),
      secondsValue = Math.floor((timeDistance % minute) / second);

    elem.innerHTML =
      countDownItem(daysValue, "days") +
      countDownItem(hoursValue, "hrs") +
      countDownItem(minutesValue, "mins") +
      countDownItem(secondsValue, "secs");

    if (timeDistance < 0) clearInterval(countDownInterval);
  }, 1000);
});

/*
  13. active class remove class activation
*/
const activeClassAction = function (toggle, target) {
  const to = document.querySelector(toggle),
    ta = document.querySelector(target);
  if (to && ta) {
    to.addEventListener("click", function (e) {
      e.preventDefault();
      let triggerItem = e.target;
      if (triggerItem.classList.contains("active")) {
        triggerItem.classList.remove("active");
        ta.classList.remove("active");
      } else {
        triggerItem.classList.add("active");
        ta.classList.add("active");
      }
    });
    document.addEventListener("click", function (event) {
      if (
        !event.target.closest(toggle) &&
        !event.target.classList.contains(toggle.replace(/\./, ""))
      ) {
        if (
          !event.target.closest(target) &&
          !event.target.classList.contains(target.replace(/\./, ""))
        ) {
          to.classList.remove("active");
          ta.classList.remove("active");
        }
      }
    });
  }
};

activeClassAction(
  ".offcanvas__account--currency__menu",
  ".offcanvas__account--currency__submenu"
);
activeClassAction(".account__currency--link", ".dropdown__currency");
activeClassAction(".language__switcher", ".dropdown__language");
activeClassAction(
  ".offcanvas__language--switcher",
  ".offcanvas__dropdown--language"
);

/*
  14. OffCanvas Sidebar Activation
*/
function offcanvsSidebar(openTrigger, closeTrigger, wrapper) {
  let OpenTriggerprimary__btn = document.querySelectorAll(openTrigger);
  let closeTriggerprimary__btn = document.querySelector(closeTrigger);
  let WrapperSidebar = document.querySelector(wrapper);
  let wrapperOverlay = wrapper.replace(".", "");

  function handleBodyClass(evt) {
    let eventTarget = evt.target;
    if (!eventTarget.closest(wrapper) && !eventTarget.closest(openTrigger)) {
      WrapperSidebar.classList.remove("active");
      document
        .querySelector("body")
        .classList.remove(`${wrapperOverlay}_active`);
    }
  }
  if (OpenTriggerprimary__btn && WrapperSidebar) {
    OpenTriggerprimary__btn.forEach(function (singleItem) {
      singleItem.addEventListener("click", function (e) {
        if (e.target.dataset.offcanvas != undefined) {
          WrapperSidebar.classList.add("active");
          document
            .querySelector("body")
            .classList.add(`${wrapperOverlay}_active`);
          document.body.addEventListener("click", handleBodyClass.bind(this));
        }
      });
    });
  }

  if (closeTriggerprimary__btn && WrapperSidebar) {
    closeTriggerprimary__btn.addEventListener("click", function (e) {
      if (e.target.dataset.offcanvas != undefined) {
        WrapperSidebar.classList.remove("active");
        document
          .querySelector("body")
          .classList.remove(`${wrapperOverlay}_active`);
        document.body.removeEventListener("click", handleBodyClass.bind(this));
      }
    });
  }
}

// Mini Cart
offcanvsSidebar(
  ".minicart__open--btn",
  ".minicart__close--btn",
  ".offCanvas__minicart"
);

// Search Bar
offcanvsSidebar(
  ".search__open--btn",
  ".predictive__search--close__btn",
  ".predictive__search--box"
);

// Offcanvas filter sidebar
offcanvsSidebar(
  ".widget__filter--btn",
  ".offcanvas__filter--close",
  ".offcanvas__filter--sidebar"
);

/*
  15. Qunatity Button Activation
*/
const quantityWrapper = document.querySelectorAll(".quantity__box");
if (quantityWrapper) {
  quantityWrapper.forEach(function (singleItem) {
    let increaseButton = singleItem.querySelector(".increase");
    let decreaseButton = singleItem.querySelector(".decrease");

    increaseButton.addEventListener("click", function (e) {
      let input = e.target.previousElementSibling.children[0];
      if (input.dataset.counter != undefined) {
        let value = parseInt(input.value, 10);
        value = isNaN(value) ? 0 : value;
        value++;
        input.value = value;
      }
    });

    decreaseButton.addEventListener("click", function (e) {
      let input = e.target.nextElementSibling.children[0];
      if (input.dataset.counter != undefined) {
        let value = parseInt(input.value, 10);
        value = isNaN(value) ? 0 : value;
        value < 1 ? (value = 1) : "";
        value--;
        input.value = value;
      }
    });
  });
}

/*
  16. Modal JS Activation
*/
const openEls = document.querySelectorAll("[data-open]");
const closeEls = document.querySelectorAll("[data-close]");
const isVisible = "is-visible";
for (const el of openEls) {
  el.addEventListener("click", function () {
    const modalId = this.dataset.open;
    document.getElementById(modalId).classList.add(isVisible);
  });
}
for (const el of closeEls) {
  el.addEventListener("click", function () {
    this.parentElement.parentElement.parentElement.classList.remove(isVisible);
  });
}
document.addEventListener("click", (e) => {
  if (e.target == document.querySelector(".modal.is-visible")) {
    document.querySelector(".modal.is-visible").classList.remove(isVisible);
  }
});
document.addEventListener("keyup", (e) => {
  if (e.key == "Escape" && document.querySelector(".modal.is-visible")) {
    document.querySelector(".modal.is-visible").classList.remove(isVisible);
  }
});

/*
  17. Accordion Activation
*/
function customAccordion(accordionWrapper, singleItem, accordionBody) {
  let accoridonButtons = document.querySelectorAll(accordionWrapper);
  accoridonButtons.forEach(function (item) {
    item.addEventListener("click", function (evt) {
      let itemTarget = evt.target;
      if (
        itemTarget.classList.contains("accordion__items--button") ||
        itemTarget.classList.contains("widget__categories--menu__label")
      ) {
        let singleAccordionWrapper = itemTarget.closest(singleItem),
          singleAccordionBody =
            singleAccordionWrapper.querySelector(accordionBody);
        if (singleAccordionWrapper.classList.contains("active")) {
          singleAccordionWrapper.classList.remove("active");
          slideUp(singleAccordionBody);
        } else {
          singleAccordionWrapper.classList.add("active");
          slideDown(singleAccordionBody);
          getSiblings(singleAccordionWrapper).forEach(function (item) {
            let sibllingSingleAccordionBody = item.querySelector(accordionBody);
            item.classList.remove("active");
            slideUp(sibllingSingleAccordionBody);
          });
        }
      }
    });
  });
}

customAccordion(
  ".accordion__container",
  ".accordion__items",
  ".accordion__items--body"
);

customAccordion(
  ".widget__categories--menu",
  ".widget__categories--menu__list",
  ".widget__categories--sub__menu"
);

/*
  18. Footer widget Activation
*/
let accordion = true;
const footerWidgetAccordion = function () {
  accordion = false;
  let footerWidgetContainer = document.querySelector(".main__footer");
  footerWidgetContainer.addEventListener("click", function (evt) {
    let singleItemTarget = evt.target;
    if (singleItemTarget.classList.contains("footer__widget--button")) {
      const footerWidget = singleItemTarget.closest(".footer__widget"),
        footerWidgetInner = footerWidget.querySelector(
          ".footer__widget--inner"
        );
      if (footerWidget.classList.contains("active")) {
        footerWidget.classList.remove("active");
        slideUp(footerWidgetInner);
      } else {
        footerWidget.classList.add("active");
        slideDown(footerWidgetInner);
        getSiblings(footerWidget).forEach(function (item) {
          const footerWidgetInner = item.querySelector(
            ".footer__widget--inner"
          );

          item.classList.remove("active");
          slideUp(footerWidgetInner);
        });
      }
    }
  });
};

window.addEventListener("load", function () {
  if (accordion) {
    footerWidgetAccordion();
  }
});
window.addEventListener("resize", function () {
  document.querySelectorAll(".footer__widget").forEach(function (item) {
    if (window.outerWidth >= 768) {
      item.classList.remove("active");
      item.querySelector(".footer__widget--inner").style.display = "";
    }
  });
  if (accordion) {
    footerWidgetAccordion();
  }
});

/*
  19. Flightbox Activation
*/
const customLightboxHTML = `<div id="glightbox-body" class="glightbox-container">
    <div class="gloader visible"></div>
    <div class="goverlay"></div>
    <div class="gcontainer">
    <div id="glightbox-slider" class="gslider"></div>
    <button class="gnext gbtn" tabindex="0" aria-label="Next" data-customattribute="example">{nextSVG}</button>
    <button class="gprev gbtn" tabindex="1" aria-label="Previous">{prevSVG}</button>
    <button class="gclose gbtn" tabindex="2" aria-label="Close">{closeSVG}</button>
    </div>
    </div>`;
const lightbox = GLightbox({
  touchNavigation: true,
  lightboxHTML: customLightboxHTML,
  loop: true,
});

/*
  20. CounterUp Activation
*/
const wrapper = document.getElementById("funfactId");
if (wrapper) {
  const counters = wrapper.querySelectorAll(".js-counter");
  const duration = 1000;

  let isCounted = false;
  document.addEventListener("scroll", function () {
    const wrapperPos = wrapper.offsetTop - window.innerHeight;
    if (!isCounted && window.scrollY > wrapperPos) {
      counters.forEach((counter) => {
        const countTo = counter.dataset.count;

        const countPerMs = countTo / duration;

        let currentCount = 0;
        const countInterval = setInterval(function () {
          if (currentCount >= countTo) {
            clearInterval(countInterval);
          }
          counter.textContent = Math.round(currentCount);
          currentCount = currentCount + countPerMs;
        }, 1);
      });
      isCounted = true;
    }
  });
}

/*
  21. Offcanvas Mobile Menu Function
*/
const offcanvasHeader = function () {
  const offcanvasOpen = document.querySelector(
      ".offcanvas__header--menu__open--btn"
    ),
    offcanvasClose = document.querySelector(".offcanvas__close--btn"),
    offcanvasHeader = document.querySelector(".offcanvas__header"),
    offcanvasMenu = document.querySelector(".offcanvas__menu"),
    body = document.querySelector("body");
  /* Offcanvas SubMenu Toggle */
  if (offcanvasMenu) {
    offcanvasMenu
      .querySelectorAll(".offcanvas__sub_menu")
      .forEach(function (ul) {
        const subMenuToggle = document.createElement("button");
        subMenuToggle.classList.add("offcanvas__sub_menu_toggle");
        ul.parentNode.appendChild(subMenuToggle);
      });
  }
  /* Open/Close Menu On Click Toggle Button */
  if (offcanvasOpen) {
    offcanvasOpen.addEventListener("click", function (e) {
      e.preventDefault();
      if (e.target.dataset.offcanvas != undefined) {
        offcanvasHeader.classList.add("open");
        body.classList.add("mobile_menu_open");
      }
    });
  }
  if (offcanvasClose) {
    offcanvasClose.addEventListener("click", function (e) {
      e.preventDefault();
      if (e.target.dataset.offcanvas != undefined) {
        offcanvasHeader.classList.remove("open");
        body.classList.remove("mobile_menu_open");
      }
    });
  }

  /* Mobile submenu slideToggle Activation */
  let mobileMenuWrapper = document.querySelector(".offcanvas__menu_ul");
  if (mobileMenuWrapper) {
    mobileMenuWrapper.addEventListener("click", function (e) {
      let targetElement = e.target;
      if (targetElement.classList.contains("offcanvas__sub_menu_toggle")) {
        const parent = targetElement.parentElement;
        if (parent.classList.contains("active")) {
          targetElement.classList.remove("active");
          parent.classList.remove("active");
          parent
            .querySelectorAll(".offcanvas__sub_menu")
            .forEach(function (subMenu) {
              subMenu.parentElement.classList.remove("active");
              subMenu.nextElementSibling.classList.remove("active");
              slideUp(subMenu);
            });
        } else {
          targetElement.classList.add("active");
          parent.classList.add("active");
          slideDown(targetElement.previousElementSibling);
          getSiblings(parent).forEach(function (item) {
            item.classList.remove("active");
            item
              .querySelectorAll(".offcanvas__sub_menu")
              .forEach(function (subMenu) {
                subMenu.parentElement.classList.remove("active");
                subMenu.nextElementSibling.classList.remove("active");
                slideUp(subMenu);
              });
          });
        }
      }
    });
  }

  if (offcanvasHeader) {
    document.addEventListener("click", function (event) {
      if (
        !event.target.closest(".offcanvas__header--menu__open--btn") &&
        !event.target.classList.contains(
          ".offcanvas__header--menu__open--btn".replace(/\./, "")
        )
      ) {
        if (
          !event.target.closest(".offcanvas__header") &&
          !event.target.classList.contains(
            ".offcanvas__header".replace(/\./, "")
          )
        ) {
          offcanvasHeader.classList.remove("open");
          body.classList.remove("mobile_menu_open");
        }
      }
    });
  }

  /* Remove Mobile Menu Open Class & Hide Mobile Menu When Window Width in More Than 991 */
  if (offcanvasHeader) {
    window.addEventListener("resize", function () {
      if (window.outerWidth >= 992) {
        offcanvasHeader.classList.remove("open");
        body.classList.remove("mobile_menu_open");
      }
    });
  }
};
offcanvasHeader();

// Category Submenu

const categoryMobileMenu = function () {
  const CategorySubMenu = document.querySelector(".category__mobile--menu");
  if (CategorySubMenu) {
    CategorySubMenu.querySelectorAll(".category__sub--menu").forEach(function (
      ul
    ) {
      let catsubMenuToggle = document.createElement("button");
      catsubMenuToggle.classList.add("category__sub--menu_toggle");
      ul.parentNode.appendChild(catsubMenuToggle);
    });
  }
  let categoryMenuWrapper = document.querySelector(
    ".category__mobile--menu_ul"
  );
  if (categoryMenuWrapper) {
    categoryMenuWrapper.addEventListener("click", function (e) {
      let targetElement = e.target;
      if (targetElement.classList.contains("category__sub--menu_toggle")) {
        const parent = targetElement.parentElement;
        if (parent.classList.contains("active")) {
          targetElement.classList.remove("active");
          parent.classList.remove("active");
          parent
            .querySelectorAll(".category__sub--menu")
            .forEach(function (subMenu) {
              subMenu.parentElement.classList.remove("active");
              subMenu.nextElementSibling.classList.remove("active");
              slideUp(subMenu);
            });
        } else {
          targetElement.classList.add("active");
          parent.classList.add("active");
          slideDown(targetElement.previousElementSibling);
          getSiblings(parent).forEach(function (item) {
            item.classList.remove("active");
            item
              .querySelectorAll(".category__sub--menu")
              .forEach(function (subMenu) {
                subMenu.parentElement.classList.remove("active");
                subMenu.nextElementSibling.classList.remove("active");
                slideUp(subMenu);
              });
          });
        }
      }
    });
  }
};
categoryMobileMenu();

/*
  22. Newsletter popup Activation
*/
const newsletterPopup = function () {
  let newsletterWrapper = document.querySelector(".newsletter__popup"),
    newsletterCloseButton = document.querySelector(
      ".newsletter__popup--close__btn"
    ),
    dontShowPopup = document.querySelector("#newsletter__dont--show"),
    popuDontShowMode = localStorage.getItem("newsletter__show");

  if (newsletterWrapper && popuDontShowMode == null) {
    window.addEventListener("load", (event) => {
      setTimeout(function () {
        document.body.classList.add("overlay__active");
        newsletterWrapper.classList.add("newsletter__show");

        document.addEventListener("click", function (event) {
          if (!event.target.closest(".newsletter__popup--inner")) {
            document.body.classList.remove("overlay__active");
            newsletterWrapper.classList.remove("newsletter__show");
          }
        });

        newsletterCloseButton.addEventListener("click", function () {
          document.body.classList.remove("overlay__active");
          newsletterWrapper.classList.remove("newsletter__show");
        });

        dontShowPopup.addEventListener("click", function () {
          if (dontShowPopup.checked) {
            localStorage.setItem("newsletter__show", true);
          } else {
            localStorage.removeItem("newsletter__show");
          }
        });
      }, 3000);
    });
  }
};
newsletterPopup();
