/*
 *  Copyright (c) 2021 Microsoft Corporation
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Microsoft Corporation - Initial implementation
 *
 */

package org.eclipse.edc.catalog.spi;

import org.eclipse.edc.catalog.spi.model.FederatedCatalogCacheQuery;
import org.eclipse.edc.connector.controlplane.asset.spi.domain.Asset;

/**
 * Accepts a {@link FederatedCatalogCacheQuery} and fetches a collection of {@link Asset} that conform to that query.
 */
@FunctionalInterface
public interface QueryEngine {

    QueryResponse getCatalog(FederatedCatalogCacheQuery query);
}
