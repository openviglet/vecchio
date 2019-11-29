vecchioApp.factory('vecAppResource', [ '$resource', 'vecAPIServerService', function($resource, vecAPIServerService) {
	return $resource(vecAPIServerService.get().concat('/v2/app/:id'), {
		id : '@id'
	}, {
		update : {
			method : 'PUT'
		}
	});
} ]);
