app.controller("authority-ctrl", function ($scope, $http, $location) {
    var url = "/rest/roles";
    var url1 = "/rest/authorities";
    var url2 = "/rest/accounts?admin=false";
    var url3 = "/rest/authorities?admin=false";
    $scope.roles = [];
    $scope.admins = [];
    $scope.authorities = [];

    var sweetalert = function (text) {
        Swal.fire({
            icon: "success",
            title: text,
            showConfirmButton: false,
            timer: 2000,
        });
    }

    $scope.initialize = function () {
        //load roles
        $http.get(url).then(resp => {
            $scope.roles = resp.data;
        });

        $http.get(url2).then(resp => {
            $scope.admins = resp.data;
        });

        $http.get(url3).then(resp => {
            $scope.authorities = resp.data;
        });
    }


    //phan trang
    $scope.pager = {
        page: 0,
        size: 10,
        get admins() {
            var start = this.page * this.size;
            return $scope.admins.slice(start, start + this.size);
        },
        get count() {
            return Math.ceil(1.0 * $scope.admins.length / this.size)
        },
        first() {
            this.page = 0;
        },
        prev() {
            this.page--;
            if (this.page < 0) {
                this.last();
            }
        },
        next() {
            this.page++;
            if (this.page >= this.count) {
                this.first();
            }
        },
        last() {
            this.page = this.count - 1;
        }
    }

});