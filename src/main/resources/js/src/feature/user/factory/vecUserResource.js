vecchioApp.factory('vecUserResource', [ '$resource', 'vecAPIServerService', function($resource, vecAPIServerService) {
	return $resource(vecAPIServerService.get().concat('/v2/user/:id'), {
		id : '@username'
	}, {
		update : {
			method : 'PUT'
		}
	});
} ]);
