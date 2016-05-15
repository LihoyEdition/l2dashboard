function DashboardController($http) {
	var ctrl = this;

	$http.get("/servers").then(function successCallback(response) {
		ctrl.serversInfo = response.data;
	});
}

const DashboardComponent = {
	templateUrl: "app/dashboard/dashboard.html",
	controller: ["$http", DashboardController]
};

angular.module("dashboard", []).component("dashboard", DashboardComponent);