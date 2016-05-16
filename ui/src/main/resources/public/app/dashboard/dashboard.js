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

angular.module("dashboard", [])
		.filter("duration", function () {
			return function (ms) {
				if (ms == 0) {
					return 0;
				}

				var seconds = Math.floor(ms / 1000);

				var days = Math.floor(seconds / 86400);
				var hours = Math.floor((seconds % 86400) / 3600);
				var minutes = Math.floor(((seconds % 86400) % 3600) / 60);

				if (days == 0 && hours == 0 && minutes == 0) {
					return "< 1 minute";
				}

				return days + "d " + hours + "h " + minutes + "m";
			};
		})
		.component("dashboard", DashboardComponent);