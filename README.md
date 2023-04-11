
# jDAV

__A DAV framework for Java__

This framework provides a convenient and extensible way to serialize and parse XML documents as used in WebDAV and standards building upon WebDAV.

## Requirements

[XmlObjects](https://github.com/dmfs/xmlobjects) for parsing and serialization.

## Supported RFCs

### Implementation status

The following matrix gives an overview over which RFCs are currently supported. A missing [X] means that some work has been done but not all XML elements are supported yet.


| RFC | RFC title | client | server |
| --- | --------- | ------ | ------- |
| [RFC 3253](https://tools.ietf.org/html/rfc3253) | Versioning Extensions to WebDAV | [ ] | [ ] |
| [RFC 3744](https://tools.ietf.org/html/rfc3744) | Access Control Protocol Extensions to WebDAV   | [ ] | [ ] |
| [RFC 4791](https://tools.ietf.org/html/rfc4791) | Calendaring Extensions to WebDAV   | [ ] | [ ] |
| [RFC 4918](https://tools.ietf.org/html/rfc4918) | WebDAV  | [ ] | [ ] |
| [RFC 5323](https://tools.ietf.org/html/rfc5323) | WebDAV SEARCH | [ ] | [ ] |
| [RFC 5397](https://tools.ietf.org/html/rfc5397) | WebDAV Current Principal Extension | [X] | [X] |
| [RFC 5689](https://tools.ietf.org/html/rfc5689) | Extended MKCOL for Web Distributed Authoring and Versioning (WebDAV) | [X] | [X] |
| [RFC 5842](https://tools.ietf.org/html/rfc5842) | Binding Extensions to Web Distributed Authoring and Versioning | [ ] | [ ] |
| [RFC 5995](https://tools.ietf.org/html/rfc5995) | Using POST to Add Members to Web Distributed Authoring and Versioning (WebDAV) Collections | [X] | [X] |
| [RFC 6352](https://tools.ietf.org/html/rfc6352) | CardDAV: vCard Extensions to Web Distributed Authoring and Versioning  | [ ] | [ ] |
| [RFC 6578](https://tools.ietf.org/html/rfc6578) | Collection Synchronization for Web Distributed Authoring and Versioning  | [X] | [X] |

## License

Copyright 2023 dmfs GmbH - http://dmfs.org

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.