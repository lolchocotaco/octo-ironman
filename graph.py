import time
import os
import subprocess

args = ['java', 'DistributedMining.Client', 'ice01.ee.cooper.edu', '7001', 'AaaaaaaaaaAaaaaaaaaaAaaaaaaaaaAaaaaaaaaaAaaaaaaaaaAaaaaaaaaaAaaaaaaaaaAaaaaaaaaa', 'THISISAREALLYLONGANDCOMPLICATEDSTRING981498THISISAREALLYLONGANDCOMPLICATEDSTRING', '30', '0.1']

start = time.time()

numRequests = 10

FNULL = open(os.devnull, 'w')

p = [None]*numRequests
for i in range(0,numRequests):
	p[i] = subprocess.Popen(args, stdout=FNULL, stderr=subprocess.STDOUT)

for i in range(0,numRequests):
	p[i].communicate();

end = time.time()

elapsed = end - start

print "Time to finish: " + elapsed
