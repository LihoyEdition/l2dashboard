function HistoryController($http, $timeout) {
	var ctrl = this;

	ctrl.historyType = new (function () {
		this.options = [
			{id: 1, name: "Registrations", url: "/history/registrations"},
			{id: 2, name: "Online", url: "/history/online"}
		];
		this.selected = this.options[0];
	})();

	ctrl.serverIds = loadServerIds();

	// TODO: Stub. Should be loaded from backend.
	function loadServerIds() {
		var serverIds = {
			options: [
				{id: 1, name: "x1"},
				{id: 2, name: "x50"},
				{id: 3, name: "x100"}
			]
		};
		serverIds.selected = serverIds.options[0];
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
					return d3.time.format("%Y-%m-%d")(new Date(d));
				},
				showMaxMin: false
			},
			x2Axis: {
				tickFormat: function (d) {
					return d3.time.format("%Y-%m-%d")(new Date(d));
				},
				showMaxMin: false
			},
			yAxis: {
				showMaxMin: false
			},
			clipEdge: false,
			average: 1000
		}
	};

	ctrl.buildChart = function () {
		var url = ctrl.historyType.selected.url;
		var config = ctrl.isHistoryOnlineSelected() ? {params: {serverId: ctrl.serverIds.selected.id}} : null;
		$http.get(url, config).then(function successCallback(response) {
			if (ctrl.isHistoryOnlineSelected()) {
				ctrl.chartData = [
					{key: "Average", values: [], mean: response.data.averageOnline},
					{key: "Maximum", values: [], mean: response.data.maxOnline}
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

			// Trick for avoiding bug when uses the same period for focusing
			var lastDate = new Date(response.data.history[response.data.history.length - 1].date);
			ctrl.chartOptions.chart.brushExtent = null;
			$timeout(function () {
				ctrl.chartOptions.chart.brushExtent = [d3.time.month.offset(lastDate, -1), lastDate];
			}, 100);
		});
	};

	ctrl.$onInit = function () {
		ctrl.buildChart();
	}
}

const HistoryComponent = {
	templateUrl: "app/history/history.html",
	controller: ["$http", "$timeout", HistoryController]
};

angular.module("history", ["nvd3"]).component("history", HistoryComponent);