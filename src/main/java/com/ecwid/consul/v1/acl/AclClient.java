package com.ecwid.consul.v1.acl;

import java.util.List;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.acl.model.AclToken;
import com.ecwid.consul.v1.acl.model.NewAcl;
import com.ecwid.consul.v1.acl.model.UpdateAcl;

/**
 * Methods for the ACL Token HTTP API
 * 
 * @author Jon Huang (jon5477)
 *
 */
public interface AclClient {
	Response<AclToken> aclCreate(NewAcl newAcl);

	Response<AclToken> aclRead(String accessorId);

	Response<AclToken> aclRead(String accessorId, boolean expanded);

	Response<AclToken> aclReadSelf();

	Response<AclToken> aclUpdate(UpdateAcl updateAcl, String accessorId);

	Response<AclToken> aclClone(@NonNull String accessorId);

	Response<AclToken> aclClone(@NonNull String accessorId, @Nullable String description);

	Response<Void> aclDelete(String accessorId);

	Response<List<AclToken>> aclList(AclTokensRequest request);
}