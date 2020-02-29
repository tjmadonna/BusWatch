package com.madonnaapps.buswatch.data.datastore.location

import com.madonnaapps.buswatch.domain.repository.LocationRepository

// LocationLocalDataStore implements LocationRepository because there's nothing special to do
// in data layer
interface LocationLocalDataStore: LocationRepository