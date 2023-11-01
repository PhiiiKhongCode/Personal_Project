var app = angular.module("banHangApp", ["ngRoute", 'ngMaterial']);
app.config(function ($routeProvider, $locationProvider) {
    $locationProvider.hashPrefix("");
    $routeProvider
        .when("/ban-hang", {
            templateUrl: 'listProduct.html',
            controller: 'banHangController'
        }).when('/gio-hang', {
            templateUrl: 'GioHang.html',
            controller: 'gioHangCtroller'
        }).when('/product/:id', {
            templateUrl: 'SPChiTiet.html',
            controller: 'productDetailController'
        }).when('/san-pham', {
            templateUrl: 'QLSanPham.html',
            controller: 'manaProductsController'
        }).when('/orders', {
            templateUrl: 'QLDonHang.html',
            controller: 'manaOrderController'
        }).when('/login', {
            templateUrl: 'LoginForm.html',
            controller: 'loginController'
        }).when('/register', {
            templateUrl: 'RegisterForm.html',
            controller: 'registerController'
        }).when('/account', {
            templateUrl: 'QLAccount.html',
            controller: 'manageAccountController'
        })
        .otherwise({
            redirectTo: '/ban-hang'
        });
});


app.service('RouteChangeService', function ($rootScope) {
    this.onRouteChange = function (callback) {
        $rootScope.$on('$routeChangeSuccess', callback);
    };
});
app.run(function (RouteChangeService, $rootScope, $window) {
    RouteChangeService.onRouteChange(function (event, current, previous) {
        // Đoạn mã bạn muốn chạy mỗi khi route thay đổi
        loadTotalCart($rootScope);
    });
    $rootScope.logout = () => {
        console.log('Logout account');
        $window.sessionStorage.removeItem('token');
    };
});

//run cart alltime
let loadTotalCart = ($rootScope) => {
    cart.loadFromLocalStorage();
    $rootScope.totalCart = cart.count;
}

//add intercepter to angularjs app
app.factory('authInterceptor', function ($q, $window) {
    return {
        request: function (config) {
            var token = $window.sessionStorage.getItem('token');
            if (token) {
                config.headers['Authorization'] = 'Bearer ' + token;
            }
            return config;
        },
        responseError: function (response) {
            if (response.status === 401) {
                // Xử lý lỗi khi không có quyền truy cập
            }
            return $q.reject(response);
        }
    };
});
app.config(function ($httpProvider) {
    $httpProvider.interceptors.push('authInterceptor');
});

//config material
app.config(function ($mdThemingProvider) {
    $mdThemingProvider.theme('default')
        .primaryPalette('blue')
        .accentPalette('orange');
});

// ban hang
app.controller('banHangController', function ($scope, $http, $location, $rootScope) {
    $rootScope.totalCart = 0;
    cart.loadFromLocalStorage();
    $rootScope.totalCart = cart.count;
    console.log(cart.count);
    console.log('>>>>>>>>>>>>>>>>>>>>>>>>>>>')
    $scope.products = []
    let apiPhone = lstApiUrlProducts + '/phone';
    let apiLaptop = lstApiUrlProducts + '/laptop';
    let apiCamera = lstApiUrlProducts + '/camera';

    $http.get(`${apiPhone}`).then(function (response) {
        if (response.status === 200) {
            $scope.pPhones = response.data;
            console.log($scope.pPhones);
        }
    }, function (errors) {
        console.log(errors)
    });
    $http.get(`${apiLaptop}`).then(function (response) {
        if (response.status === 200) {
            $scope.pLaptops = response.data;
        }
    }, function (errors) {
        console.log(errors)
    });
    $http.get(`${apiCamera}`).then(function (response) {
        if (response.status === 200) {
            $scope.pCameras = response.data;
        }
    }, function (errors) {
        console.log(errors)
    });

    $scope.add = (id) => {
        let item;
        if (cart.items.length > 0) {
            item = cart.items.find(ele => ele.id == id)
            if (item) {
                item.qtt++;
                cart.saveToLocalStorage;
                cart.saveToLocalStorage();
                cart.loadFromLocalStorage();

            } else {
                var api = lstApiUrlProducts + '/' + id;
                $http.get(api).then(function (response) {
                    let x = angular.copy(response.data);
                    x.qtt = 1;

                    cart.items.push(x);
                    cart.saveToLocalStorage();
                    cart.loadFromLocalStorage();
                })
            }
        } else {
            var api = lstApiUrlProducts + '/' + id;
            $http.get(api).then(function (response) {
                let x = angular.copy(response.data);
                x.qtt = 1;
                console.log(x);

                cart.items = new Array();
                cart.items.push(x);

                console.log('Array is empty')
                console.log(cart.items)
                cart.saveToLocalStorage();
                cart.loadFromLocalStorage();
            })
            $rootScope.totalCart = cart.count;
        }

        $rootScope.totalCart = cart.count();
    }

    $scope.detail = (id) => {
        console.log("Run detail")
        $location.path('/product/' + id);
    }


});
// directive swiper ban hang
app.directive("swiper", function () {
    return {
        //     restrict: 'A',
        link: function (scope, element, attrs) {
            const swiper = new Swiper('.swiper', {
                // Optional parameters
                // loop: true, 
                observer: true,
                observeParents: true,
                freeMode: true,
                speed: 1000,
                watchSlidesVisibility: true,
                watchSlidesProgress: true,
                slidesPerView: 4,
                direction: 'horizontal',
                parallax: true,
                autoplay: {
                    delay: 3000,
                    waitForTransaction: false,
                    pauseOnMouseEnter: false
                },
                breakpoints: {
                    // when window width is >= 320px
                    320: {
                        slidesPerView: 1
                    },
                    720: {
                        slidesPerView: 1.5
                    },
                    // when window width is >= 480px
                    800: {
                        slidesPerView: 2.5
                    },
                    // when window width is >= 640px
                    960: {
                        slidesPerView: 4
                    }
                },

                // speed: 600,

                // If we need pagination
                // pagination: {
                //     el: '.swiper-pagination',
                //     clickable: true,
                // },

                // Navigation arrows
                navigation: {
                    nextEl: '.swiper-button-next',
                    prevEl: '.swiper-button-prev',
                }

                // And if we need scrollbar
                // scrollbar: {
                //     el: '.swiper-scrollbar',
                // },

            });
            swiper.autoplay.start();
        }
    }
});

//bat su kien back
// app.run(function ($rootScope, $location, $window) {
//     $window.onbeforeunload = function () {
//         var isHomePage = ($location.pathname === '/ban-hang' || $location.hash === '#/ban-hang');
//         if (isHomePage) {
//             $window.location.reload();
//         }
//     };
// });


//controller gio hang
app.controller('gioHangCtroller', function ($scope, $rootScope, $location, $http, $mdToast) {
    $scope.cartDetails = cart.items;

    $scope.saveToLocalStorage = () => {
        cart.saveToLocalStorage();
        $rootScope.totalCart = cart.count;
    }

    $scope.remove = (item) => {
        cart.remove(item);
        $rootScope.totalCart = cart.count;
    }

    let toast = $mdToast.simple()
        .textContent(`You are not logged in! Please login`)
        .position('top left')
        .hideDelay(4000); // 3 giây

    $scope.addToOrder = (order) => {
        let createDate = new Date();
        order.createDate = createDate
        order.status = true;
        let cartsAndOrderRequest = { cartDetails: $scope.cartDetails, order: order };
        $http.post(lstApiUrlOrders, cartsAndOrderRequest).then(function (response) {
            if (response.status === 201) {
                console.log('Add order successful')
                $location.path('/orders');
            }
        }, function (errors) {
            $mdToast.show(toast)
            $location.path('/login')
            console.log(errors)
        })
    }
});


//entity cart
var cart = {
    items: [],

    remove(item) {
        let index = this.items.findIndex(ele => ele.id === item.id);
        this.items.splice(index, 1);
        this.saveToLocalStorage();
    },
    clear() {
        this.items = [];
    },

    //tinh tong so luong cac mat hang
    get count() {
        return this.items.map(item => item.qtt).reduce((acc, qtt) => acc += qtt, 0);
    },
    //tong thanh tien cac mat hang trong gio
    get mount() {
        return this.items.map(item => item.qtt * item.price)
            .reduce((total, qtt) => total += qtt, 0);
    },
    //luu gio hang vao localstorage
    saveToLocalStorage() {
        var json = JSON.stringify(angular.copy(this.items));
        localStorage.setItem('cart', json);
    },
    //load from local storage
    loadFromLocalStorage() {
        console.log('start load localstorage')
        var json = localStorage.getItem('cart');
        this.items = json ? JSON.parse(json) : [];
    }
}


//controller product detail
app.controller('productDetailController', function ($scope, $http, $routeParams) {
    var api = lstApiUrlProducts + '/' + $routeParams.id;
    $http.get(api).then(function (response) {
        if (response.status === 200) {
            $scope.pDetail = angular.copy(response.data);
        }
    }, function (errors) {
        console.log(errors)
    })

    $scope.addToCart = (id) => {
        let item;
        if (cart.items.length > 0) {
            item = cart.items.find(ele => ele.id == id)
            if (item) {
                item.qtt = typeof $scope.pDetail.qtt === 'undefined' ? 1 : $scope.pDetail.qtt;
                cart.saveToLocalStorage;
                cart.saveToLocalStorage();
                cart.loadFromLocalStorage();

            } else {
                let x = angular.copy($scope.pDetail);
                x.qtt = typeof $scope.pDetail.qtt === 'undefined' ? 1 : $scope.pDetail.qtt;

                cart.items.push(x);
                cart.saveToLocalStorage();
                cart.loadFromLocalStorage();
            }
        } else {
            let x = angular.copy($scope.pDetail);
            x.qtt = typeof $scope.pDetail.qtt === 'undefined' ? 1 : $scope.pDetail.qtt;

            cart.items = new Array();
            cart.items.push(x);

            cart.saveToLocalStorage();
            cart.loadFromLocalStorage();
        }
        $rootScope.totalCart = cart.count;
    }

});
//controller quan ly san pham
app.controller('manaProductsController', function ($scope, $http) {

    $scope.products = [];
    $scope.categories = [];
    //findAll categories
    $scope.findCategories = () => {
        return $http.get(lstApiUrlCategories).then(function (response) {
            if (response.status === 200) {
                $scope.categories = response.data;
            }
        }, function (errors) {
            console.log(errors);
        })
    };
    $scope.findCategories();

    $scope.findAll = () => {
        return $http.get(lstApiUrlProducts).then(function (response) {
            if (response.status === 200) {
                $scope.products = response.data;
            }
        }, function (errors) {
            console.log(errors);
        })
    };
    $scope.findAll();

    // filter products 
    $scope.findByAvai = (avai) => {
        $scope.products = [];

        if (avai) {
            let api = lstApiUrlProducts + '/lst/' + avai;
            return $http.get(api).then(function (response) {
                if (response.status === 200) {
                    $scope.products = response.data;
                }
            }, function (errors) {
                console.log(errors);
            })
        } else {
            $scope.findAll();
        }
    }

    $scope.add = (item) => {
        let category = $scope.categories.find(x => x.id === item.category);
        item.category = category;
        item.createDate = new Date();

        $http.post(lstApiUrlProducts, item).then(function (response) {
            if (response.status === 201) {
                console.log(response)
            }
        }, function (errors) {
            console.log(errors);
        })
        $scope.findByAvai();
    }

    $scope.edit = (item) => {
        $scope.pUp = item;
        return;
    }

    $scope.update = (item) => {
        let category = $scope.categories.find(x => x.id === item.category.id);
        item.category = category;

        return $http.put(lstApiUrlProducts, item).then(function (response) {
            if (response.status === 200) {
                console.log(response);
                reload();
            }
        }, function (errors) {
            console.log(errors);
        });
    }

    var reload = () => {
        window.location.reload();
    }
});

//controller quan ly don hang
app.controller('manaOrderController', function ($scope, $http) {



    $http.get(lstApiUrlOrders).then(function (response) {
        if (response.status === 200) {
            $scope.orders = response.data;
            console.log(response)
        }
    }, function (errors) {
        console.log(errors);
    });
});


//controller login
app.controller('loginController', function ($scope, $http, $window, $mdToast, $location) {
    //toast 
    let toast = $mdToast.simple()
        .textContent('Login successful!')
        .position('top left')
        .hideDelay(3000); // 3 giây

    $scope.login = (requestLogin) => {
        $http.post(lstApiUrlLogin, requestLogin)
            .then(function (response) {
                if (response.status === 200) {
                    let token = response.data.token;
                    $window.sessionStorage.setItem('token', token);
                    console.log(token)
                    $mdToast.show(toast);
                    $location.path('/ban-hang')
                }
            }, function (errors) {
                console.log(errors);
            });
    };
});

//register controller
app.controller('registerController', function ($scope, $http, $location, $mdToast) {
    //toast 
    let toast = $mdToast.simple()
        .textContent('Sign up successful! Please login to use feature.')
        .position('top left')
        .hideDelay(3000); // 3 giây

    $scope.register = (signUpRequest, role1, role2, role3) => {
        if (role1 === undefined) signUpRequest.roleStr[0] = role1;
        if (role2 === undefined) signUpRequest.roleStr[1] = role2;
        if (role3 === undefined) signUpRequest.roleStr[2] = role3;

        $http.post(lstApiUrlRegister, signUpRequest).then(function (response) {
            if (response.status === 200) {
                $mdToast.show(toast);
                $location.path('/login');
            };
        }, function (errors) {
            $location.path('/login');
            console.log(errors)
        })
    }
});



app.controller('manageAccountController', function ($scope, $http, $location) {

    $http.get(lstApiUrlManageAccount).then(function (response) {
        if (response.status === 200) {
            $scope.account = response.data;
        }
    }, function (errors) {
        $location.path("/login")
        console.log(errors);
    })

    $scope.update = (account) => {
        $http.put(lstApiUrlManageAccount, account).then(function (response) {
            if (response.status === 200) {
                $scope.account = response.data;
            }
        }, function (errors) {
            console.log(errors);
        })
    }

    $scope.disable = () => {
        // Xóa mục
        console.log("Xóa mục")
        $http.delete(lstApiUrlManageAccount).then(function (response) {
            if (response.status === 200) {
                logout();
                $location.path("/login");
            }
        }, function (errors) {
            console.log(errors);
        })
    }
});













