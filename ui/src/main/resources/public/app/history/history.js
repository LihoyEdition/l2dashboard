function HistoryController($http) {
	var ctrl = this;

	ctrl.historyType = new (function () {
		this.options = [
			{id: 1, name: "Registrations", url: "/history/registrations"},
			{id: 2, name: "Online", url: "/history/online"}
		];
		this.selected = this.options[0];
	})();

	ctrl.serverIds = loadServerIds();

	function loadServerIds() {
		var serverIds = {};
		$http.get("/servers").then(function successCallback(response) {
			serverIds.options = response.data.map(function (server) {
				return {id: server.id, name: server.name};
			});
			serverIds.selected = serverIds.options[0];
		});
		return serverIds;
	}

	ctrl.isHistoryOnlineSelected = function () {
		return ctrl.historyType.selected.id == 2;
	};

	ctrl.chartOptions = {
		chart: {
			type: "lineWithFocusChart",
			height: 450,
			duration: 500,
			useInteractiveGuideline: true,
			x: function (data) {
				return new Date(data.date);
			},
			xAxis: {
				tickFormat: function (d) {
					return d3.time.format("%d.%m.%y")(new Date(d));
				},
				showMaxMin: false
			},
			x2Axis: {
				tickFormat: function (d) {
					return d3.time.format("%d.%m.%y")(new Date(d));
				},
				showMaxMin: false
			},
			yAxis: {
				showMaxMin: false
			},
			clipEdge: false
		}
	};

	ctrl.buildChart = function () {
		var url = ctrl.historyType.selected.url;
		var config = ctrl.isHistoryOnlineSelected() ? {params: {serverId: ctrl.serverIds.selected.id}} : null;
		$http.get(url, config).then(function successCallback(response) {
			if (ctrl.isHistoryOnlineSelected()) {
				ctrl.chartData = [
					{key: "Average", values: []},
					{key: "Maximum", values: []}
				];
				response.data.history.forEach(function (history) {
					ctrl.chartData[0].values.push({date: history.date, y: history.averageOnline});
					ctrl.chartData[1].values.push({date: history.date, y: history.maxOnline});
				});
				ctrl.chartOptions.chart.y = function (data) {
					return data.y;
				};
			} else {
				ctrl.chartData = [
					{key: ctrl.historyType.selected.name, values: response.data.history}
				];
				ctrl.chartOptions.chart.y = function (data) {
					return data.registrationCount;
				};
			}

			var lastDate = new Date(response.data.history[response.data.history.length - 1].date);
			ctrl.chartOptions.chart.brushExtent = [d3.time.month.offset(lastDate, -1), lastDate];

			ctrl.api.refresh();
		});
	};

	ctrl.$onInit = function () {
		ctrl.buildChart();
	}
}

const HistoryComponent = {
	templateUrl: "app/history/history.html",
	controller: ["$http", HistoryController]
};

angular.module("history", ["nvd3"]).component("history", HistoryComponent);