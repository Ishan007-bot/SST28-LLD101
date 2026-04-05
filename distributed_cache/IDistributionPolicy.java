interface IDistributionPolicy {
    int getNodeIndex(IKey key, int totalNodes);
}