package org.dmfs.dav.rfc3253;

import org.dmfs.httpessentials.HttpMethod;
import org.saynotobugs.confidence.junit5.engine.Confidence;
import org.saynotobugs.confidence.junit5.engine.Verifiable;

import static org.dmfs.dav.rfc3253.WebDavVersioning.METHOD_REPORT;
import static org.saynotobugs.confidence.junit5.engine.ConfidenceEngine.assertThat;
import static org.saynotobugs.confidence.quality.Core.equalTo;
import static org.saynotobugs.confidence.quality.Core.has;


@Confidence
class WebDavVersioningTest
{

    Verifiable METHOD_REPORT_is_safe = assertThat(METHOD_REPORT,
        has("is safe", HttpMethod::isSafe, equalTo(true)));

}