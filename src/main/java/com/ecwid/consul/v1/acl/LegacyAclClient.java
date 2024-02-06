package com.ecwid.consul.v1.acl;

import java.util.List;

import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.acl.model.legacy.LegacyAcl;
import com.ecwid.consul.v1.acl.model.legacy.LegacyNewAcl;
import com.ecwid.consul.v1.acl.model.legacy.LegacyUpdateAcl;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public interface LegacyAclClient {
	Response<String> aclCreate(LegacyNewAcl newAcl, String token);

	Response<Void> aclUpdate(LegacyUpdateAcl updateAcl, String token);

	Response<Void> aclDestroy(String aclId, String token);

	Response<LegacyAcl> getAcl(String id);

	Response<String> aclClone(String aclId, String token);

	Response<List<LegacyAcl>> getAclList(String token);
}