#!/usr/bin/env python

import urllib2
import json
import base64
import sys
import ssl

#if not len(sys.argv) == 3:
#  print 'usage: script <username> <password>'
#  exit(1)

username = 'admin';
password = 'admin';

epass = base64.b64encode(username + ':' + password)
print ('base64 encoded: ' + epass)
baseUrl = '14a6008x60mglr0:8443'

url = 'https://' + baseUrl + '/cli/application/info' + '?application=AppDemo'

ssl._create_default_https_context = ssl._create_unverified_context
opener = urllib2.build_opener(urllib2.HTTPHandler)
req = urllib2.Request(url)
req.add_header('Authorization', 'Basic '+epass)
req.get_method = lambda: 'GET'

resp = opener.open(req)
print resp.read()