var myApp = angular.module('myApp', []);

myApp.factory("httpInterceptor", ["$q", "$rootScope", function ($q, $rootScope) {
    return {
        request: function (config) {
            return config || $q.when(config);
        },
        requestError: function (rejection) {
            return $q.reject(rejection)
        },
        response: function (response) {
            if (response.data.errCode === '9560') {
                window.parent.location.href = "/jointmodel/login.html";
                return;
            }
            return response || $q.when(response);
        },
        responseError: function (rejection) {
            return $q.reject(rejection);
        }
    };
}]);

myApp.config(["$httpProvider", function ($httpProvider) {
    $httpProvider.interceptors.push("httpInterceptor");
}]);

myApp.controller('logIn', function ($scope, $http, $rootScope, $interval) {

    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg))
            return unescape(arr[2]);
        else
            return null;
    }
    
    $scope.username = getCookie("name");
    $scope.jupyter = getCookie("jupyter");
    
    function smal_send() {
    	
    	if ($scope.jupyter == 'undefined' || $scope.jupyter == "" || $scope.jupyter == null) {
    		return;
    	}
    	
    	var form = $("<form action='" + $scope.jupyter + "/logout' method='get'>" +
    	"</form> ");
    	$("#SMAL").remove(); //如果已存在iframe则将其移除
    	$("body").append("<iframe id='SMAL' name='SMAL' style='display: none'></iframe>"); //载入iframe
    	(function () {
    		$("#SMAL").contents().find('body').html(form); //将form表单塞入iframe;
    		$("#SMAL").contents().find('form').submit(); //提交数据
    	}());
    }

    $scope.login = function () {
        var strTxtPass = ($("#txtPass").val());
        var strTxtName = encodeURI($("#txtName").val());
        $('#submit').attr('disabled', true);
        var params = {
            username: strTxtName,
            password: strTxtPass
        }
        smal_send();
        setTimeout(function () { //如果需要可以跳转到demo.com
        	$http.post("/jointmodel/user/ajaxLogin", params).success(function (strValue) {
        		if (strValue.success == true) {
        			$scope.setCookie("name", strTxtName);
        			$scope.setCookie("token", strTxtPass);
        			$scope.setCookie("jupyter", strValue.result);
        			location.href = "/jointmodel/";
        		} else {
        			$("#divError").show().html("用户名或密码错误！");
        			$('#submit').attr('disabled', false);
        		}
        	});
        }, 500)
        
    }
    $('#submit').attr('disabled', false);

    $scope.setCookie = function (name, value) {
        var exp = new Date();
        exp.setTime(exp.getTime() + 30000 * 60 * 1000);
        document.cookie = name + "=" + escape(value) + ";expires=" +
            exp.toGMTString() + ";path=/";
    }

    $scope.logOut = function () {
        $('#logoutButton').attr('disabled', true);
        smal_send();

        setTimeout(function () { //如果需要可以跳转到demo.com
            $http.post("/jointmodel/user/ajaxLoginOut").success(function (strValue) {
                if (strValue.success == true) {
                    location.href = "/jointmodel/login.html";
                }
            });
        }, 500)

    }

});


myApp.controller('showViables', function ($scope, $http, $rootScope, $interval) {
    $scope.result = 3;

    $scope.increment = function () {
        $http.get("/jointmodel/variable/getProgressRatio", {
            params: {
                taskId: $rootScope.taskID
            }
        }).success(function (data) {
            var va = data.result;
            $scope.result = data.result;
            if (va.status == 1 || va.status == 2) {
                var vaValue = va.ratio;
                if (vaValue > 2 && vaValue < 100) {
                    $("#prog").css("width", vaValue + "%").text(vaValue + "%");
                    return;
                }
                if (vaValue == 100) {
                    $("#prog").css("width", vaValue + "%").text(vaValue + "%");
                    $('#closeProg').attr('disabled', false);
                    setTimeout(doneShow, 1000);
                    $interval.cancel($scope.interval);
                }
            } else if ($scope.result.status == 0) {
                $('#closeProg').attr('disabled', false);
                $("#taskFailedErrorMsg").text("处理失败 " + $scope.result.errorMsg);
                $("#taskFailed").modal("show");
                $("#loadingModel").modal('hide');
                $interval.cancel($scope.interval);
            }
        });


    }

    function doneShow() {
        $("#loadingModel").modal('hide');
        $("#prepareDone").modal("show");
    }

    function checkRunning() {
        $http.get("/jointmodel/variable/checkRuningTask").success(function (data) {
            var result = data.result;
            if (result.status == 2) {
                $rootScope.taskID = result.taskId;
                $("#loadingModel").modal({
                    backdrop: 'static',
                    keyboard: false
                });
                $("#loadingModel").modal('show');
                $scope.interval = $interval($scope.increment, 5000);
            }
        });
    }

    checkRunning();

    // 开始准备数据
    $scope.prepareData = function () {
        var varSelected = $("#table").treegrid('getCheckedNodes');
        if (varSelected.length < 1) {
            alert("请先选择变量：");
            return;
        }
        var miguan = new Array();
        var greyScore = new Array();
        var consumerTag = new Array();
        var riskVar = new Array();

        for (var i = 0; i < varSelected.length; i++) {
            if (varSelected[i]._parentId == -1) {
                miguan.push(varSelected[i].id);
            } else if (varSelected[i]._parentId == -2) {
                greyScore.push(varSelected[i].id);
            } else if (varSelected[i]._parentId == -3) {
                riskVar.push(varSelected[i].id);
            } else if (varSelected[i]._parentId == -4) {
                consumerTag.push(varSelected[i].id);
            }
        }
        var encry = $('input:radio[name="encrypt"]:checked').val();
        var tmp = $("#select-file option:selected").text();
        $rootScope.fileNameSelected = tmp.split("~")[0];
        var params = {
            fileName: $rootScope.fileNameSelected,
            encryType: parseInt(encry),
            consumerTagIds: consumerTag,
            greyScoreV3Ids: greyScore,
            miguanVariableIds: miguan,
            riskVarIds: riskVar
        }
        $("#loadingModel").modal({
            backdrop: 'static',
            keyboard: false
        });
        $("#loadingModel").modal('show');
        $("#prog").css("width", "2%").text("2%");
        $http.post("/jointmodel/variable/prepareData", params).success(function (strValue) {
            if (strValue.success == true) {
                $rootScope.taskID = strValue.result;
                $scope.value = 0;
                // $scope.interval = setInterval($scope.increment(),5000);
                $scope.interval = $interval($scope.increment, 5000);
            } else {
                $("#loadingModel").modal('hide');
                alert("后台异常");
            }
        });
    }


    // 保存用户选择的变量
    $scope.saveVars = function () {
        var all = $("#table").treegrid('getCheckedNodes');
        if (all.length < 1) {
            alert("请选择变量");
            return;
        }
        var validvars = $scope.getValidVars(all);
        $http.post("/jointmodel/variable/saveVars", validvars).success(function (strValue) {
            if (strValue.success == true) {
                alert("变量保存成功！");
            }
        });

    }
    // 载入用户上次选择的变量
    $scope.loadVars = function () {
        $http.get("/jointmodel/variable/importVars").success(function (data) {
            if (data.success == true) {
                var ids = data.result;
                for (var i = 0; i < ids.length; i++) {
                    $("#table").treegrid('checkNode', [ids[i]]);
                }
            }
        });
    }

    $scope.getValidVars = function (vars) {
        var varFilter = new Array();
        for (var i = 0; i < vars.length; i++) {
            if (vars[i].id > 0) {
                varFilter.push(vars[i]);
            }
        }
        return varFilter;
    }

    // 下载样例
    $scope.download = function () {
        $http.get("/jointmodel/variable/api/download", {
            params: {
                "fileName": "sample.csv"
            }

        }).success(function (data) {
            var blob = new Blob([data], {
                type: "application/comma-separated-values"
            });
            var url = URL.createObjectURL(blob);
            var a = document.createElement('a');
            a.setAttribute('style', 'display:none');
            a.setAttribute('href', url);
            var filename = 'sample.csv';
            a.setAttribute('download', filename);
            a.click();
            URL.revokeObjectURL(url);
        });
    };

    // var categoryMap = new Map();
    // categoryMap.set('1', '蜜罐-1');
    // categoryMap.set('2', '灰度分-2');
    // categoryMap.set('3', '风控变量-3');
    // categoryMap.set('4', '消费标签-4');
    // $rootScope.variableTypeNs = ["布尔型-0", "整型-1", "长整型-2", "单精度浮点型-3",
    //     "双精度浮点型-4", "字符型-5", "未知-6"];
    // $rootScope.variableCategoryNs = ["蜜罐-1", "灰度分-2", "风控变量-3", "消费标签-4"];

});