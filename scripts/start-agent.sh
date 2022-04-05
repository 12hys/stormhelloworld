#!/bin/bash

# Command to run a buildkite-agent in a local Docker container.
# Be sure to add the agent token to the BK_TOKEN environment variable.
docker run \
-v "/Users/rhys/.ssh/id_rsa:/buildkite-secrets/id_rsa_buildkite_git:ro" \
-v "/Users/rhys/Code/stormhelloworld/hooks/environment:/buildkite/hooks/environment:ro" \
-t --name buildkite-agent buildkite/agent:3-ubuntu \
start --token "$(echo $BK_TOKEN)"
