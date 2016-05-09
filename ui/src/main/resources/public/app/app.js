angular.module("app", ["ngComponentRouter", "dashboard", "history"])

		.value("$routerRootComponent", "app")

		.component("app", {
			templateUrl: "app/app.html",
			$routeConfig: [
				{path: "/", name: "Dashboard", component: "dashboard", useAsDefault: true},
				{path: "/history", name: "History", component: "history"}
			],
			controller: ["$rootRouter", function ($rootRouter) {
				this.isActive = function (linkParams) {
					return $rootRouter.isRouteActive($rootRouter.generate(linkParams));
				};
			}]
		});