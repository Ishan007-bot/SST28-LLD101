class ModDistribution implements IDistributionPolicy {

    @Override
    public int getNodeIndex(IKey key, int totalNodes) {
        return Math.abs(key.hashCode()) % totalNodes;
    }
}