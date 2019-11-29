vecchioApp.factory('vecGroupResource', [ '$resource', 'vecAPIServerService', function($resource, vecAPIServerService) {
	return $resource(vecAPIServerService.get().concat('/v2/group/:id'), {
		id : '@id'
	}, {
		update : {
			method : 'PUT'
		}
	});
} ]);
